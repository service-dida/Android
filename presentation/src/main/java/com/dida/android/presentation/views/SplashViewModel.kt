package com.dida.android.presentation.views

import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckVersionAPI
import com.dida.domain.usecase.main.DeviceTokenAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionAPI: CheckVersionAPI,
    private val deviceTokenAPI: DeviceTokenAPI,
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _appVersion = MutableStateFlow<Int>(0)
    val appVersion: SharedFlow<Int> = _appVersion

    private val _navigateToHome = MutableSharedFlow<Boolean>()
    val navigateToHome: SharedFlow<Boolean> = _navigateToHome


    fun checkVersion() {
        viewModelScope.launch {
            versionAPI()
                .onSuccess { _appVersion.emit(it.version) }
                .onError { e -> catchError(e) }
        }
    }

    fun setDeviceToken(token: String) {
        baseViewModelScope.launch {
            dataStorePreferences.getAccessToken()?.let {
                if(it != "") {
                    deviceTokenAPI(token)
                        .onSuccess { dataStorePreferences.setFcmToken(token) }
                        .onError { e -> catchError(e) }
                }
            }
            _navigateToHome.emit(true)
        }
    }
}