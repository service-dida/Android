package com.dida.splash

import com.dida.common.base.BaseViewModel
import com.dida.domain.flatMap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.AppVersionUseCase
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.GetKeywordsUseCase
import com.dida.domain.usecase.PatchDeviceTokenUseCase
import com.dida.domain.usecase.RefreshTokenUseCase
import com.dida.domain.usecase.local.GetTokenUseCase
import com.dida.domain.usecase.local.SetKeywordsUseCase
import com.dida.domain.usecase.local.SetTokenUseCase
import com.dida.domain.usecase.local.SetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionUseCase: AppVersionUseCase,
    private val patchDeviceTokenUseCase: PatchDeviceTokenUseCase,
    private val commonProfileUseCase: CommonProfileUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getKeywordsUseCase: GetKeywordsUseCase,
    private val setKeywordsUseCase: SetKeywordsUseCase,
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _splashScreenGone: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val splashScreenGone: StateFlow<Boolean> = _splashScreenGone.asStateFlow()

    private val _forceUpdate: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val forceUpdate: SharedFlow<Boolean> = _forceUpdate.asSharedFlow()

    private val _navigateToHome: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val navigateToHome: SharedFlow<Boolean> = _navigateToHome.asSharedFlow()

    fun onVersionCheck(versionId: Long) = baseViewModelScope.launch {
        appVersionUseCase(versionId = versionId)
            .onSuccess {
                if (it.essentialUpdate) _splashScreenGone.emit(true)
                _forceUpdate.emit(it.essentialUpdate)
            }
            .onError { e -> catchError(e) }
    }

    fun onAppSetUp(deviceToken: String) {
        baseViewModelScope.launch {
            getKeywordsUseCase()
                .onSuccess { setKeywordsUseCase(it) }
                .flatMap { getTokenUseCase() }
                .onSuccess { (accessToken, refreshToken) ->
                    if (accessToken == null || refreshToken == null) {
                        onGoneSplash()
                        return@launch
                    }
                }
                .flatMap { (accessToken, refreshToken) -> refreshTokenUseCase(refreshToken = refreshToken ?: "") }
                .onSuccess { setTokenUseCase(accessToken = it.accessToken, refreshToken = it.refreshToken) }
                .flatMap { patchDeviceTokenUseCase(deviceToken = deviceToken) }
                .flatMap { commonProfileUseCase() }
                .onSuccess {
                    setUserIdUseCase(it.memberInfo.memberId)
                    onGoneSplash()
                }
                .onError { e ->
                    _splashScreenGone.emit(true)
                    catchError(e)
                }
        }
    }

    fun onGoogleServiceError() {
        baseViewModelScope.launch {
            onGoneSplash()
        }
    }

    private suspend fun onGoneSplash() {
        _navigateToHome.emit(true)
        _splashScreenGone.emit(true)
    }
}
