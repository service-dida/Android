package com.dida.splash

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.domain.flatMap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckVersionAPI
import com.dida.domain.usecase.main.DeviceTokenAPI
import com.dida.domain.usecase.main.RefreshTokenAPI
import com.dida.domain.usecase.main.UserProfileAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionAPI: CheckVersionAPI,
    private val deviceTokenAPI: DeviceTokenAPI,
    private val userProfileAPI: UserProfileAPI,
    private val refreshTokenAPI: RefreshTokenAPI,
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _splashScreenGone: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val splashScreenGone: StateFlow<Boolean> = _splashScreenGone.asStateFlow()

    private val _appVersion: MutableStateFlow<Int> = MutableStateFlow(0)
    val appVersion: SharedFlow<Int> = _appVersion.asStateFlow()

    private val _navigateToHome: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val navigateToHome: SharedFlow<Boolean> = _navigateToHome.asSharedFlow()

    fun checkVersion() {
        baseViewModelScope.launch {
            versionAPI()
                .onSuccess { _appVersion.emit(it.version) }
                .onError { e -> catchError(e) }
        }
    }

    fun setDeviceToken(deviceToken: String) {
        baseViewModelScope.launch {
            val accessToken = dataStorePreferences.getAccessToken()
            val refreshToken = dataStorePreferences.getRefreshToken()
            refreshToken?.let {
                refreshTokenAPI.invoke(request = it)
                    .onSuccess { response ->
                        dataStorePreferences.setAccessToken(
                            accessToken = response.accessToken ?: "",
                            refreshToken = response.refreshToken ?: ""
                        )
                    }.flatMap { deviceTokenAPI(deviceToken = deviceToken) }
                    .flatMap { userProfileAPI() }
                    .onSuccess {
                        dataStorePreferences.setUserId(it.userId)
                        _navigateToHome.emit(true)
                        _splashScreenGone.emit(true)
                    }.onError { e -> catchError(e) }
            }
            if (accessToken == null) {
                _navigateToHome.emit(true)
                _splashScreenGone.emit(true)
            }
        }
    }
}
