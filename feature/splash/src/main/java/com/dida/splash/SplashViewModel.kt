package com.dida.splash

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.domain.flatMap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckVersionAPI
import com.dida.domain.usecase.main.DeviceTokenAPI
import com.dida.domain.usecase.main.UserProfileAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionAPI: CheckVersionAPI,
    private val deviceTokenAPI: DeviceTokenAPI,
    private val userProfileAPI: UserProfileAPI
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
            dataStorePreferences.getAccessToken()?.let { accessToken ->
                if (accessToken.isNotBlank()) {
                    deviceTokenAPI(deviceToken = deviceToken)
                        .onSuccess { dataStorePreferences.setFcmToken(token = deviceToken) }
                        .flatMap { userProfileAPI() }
                        .onSuccess {
                            dataStorePreferences.setUserId(it.userId)
                            _navigateToHome.emit(true)
                            _splashScreenGone.emit(true) }
                        .onError { e -> catchError(e) }
                }
            }
        }
    }
}
