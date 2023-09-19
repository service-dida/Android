package com.dida.splash

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.domain.flatMap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.PatchDeviceTokenUseCase
import com.dida.domain.usecase.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val patchDeviceTokenUseCase: PatchDeviceTokenUseCase,
    private val commonProfileUseCase: CommonProfileUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _splashScreenGone: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val splashScreenGone: StateFlow<Boolean> = _splashScreenGone.asStateFlow()

    private val _appVersion: MutableStateFlow<Int> = MutableStateFlow(0)
    val appVersion: SharedFlow<Int> = _appVersion.asStateFlow()

    private val _navigateToHome: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val navigateToHome: SharedFlow<Boolean> = _navigateToHome.asSharedFlow()

    // TODO : 버전 체크 API 추가 필요
    fun onVersionCheck() {
        baseViewModelScope.launch {
            _appVersion.emit(0)
        }
    }

    fun onAppSetUp(deviceToken: String) {
        baseViewModelScope.launch {
            dataStorePreferences.getRefreshToken()?.let { token ->
                refreshTokenUseCase(refreshToken = token)
                    .onSuccess { response ->
                        dataStorePreferences.setAccessToken(
                            accessToken = response.accessToken ?: "",
                            refreshToken = response.refreshToken ?: ""
                        ) }
                    .flatMap { patchDeviceTokenUseCase(deviceToken = deviceToken) }
                    .flatMap { commonProfileUseCase() }
                    .onSuccess {
                        dataStorePreferences.setUserId(it.memberInfo.memberId)
                        onGoneSplash() }
                    .onError { e -> catchError(e) }
            }
            if (dataStorePreferences.getAccessToken() == null) {
                onGoneSplash()
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
