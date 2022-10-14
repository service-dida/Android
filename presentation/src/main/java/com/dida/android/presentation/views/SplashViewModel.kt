package com.dida.android.presentation.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.domain.model.splash.AppVersionResponse
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

    private val _appVersion = MutableSharedFlow<AppVersionResponse>()
    val appVersion: SharedFlow<AppVersionResponse> = _appVersion

    private val _navigateToHome = MutableSharedFlow<Boolean>()
    val navigateToHome: SharedFlow<Boolean> = _navigateToHome


    fun checkVersion() {
        viewModelScope.launch {
            showLoading()
            versionAPI()
                .onSuccess { _appVersion.emit(it) }
                .onError { e -> catchError(e) }
        }
    }

    fun setDeviceToken(token: String) {
        baseViewModelScope.launch {
            showLoading()
            dataStorePreferences.getAccessToken()?.let {
                if(it != "") {
                    deviceTokenAPI(token)
                        .onSuccess { dataStorePreferences.setFcmToken(token) }
                        .onError { e -> catchError(e) }
                }
            }
            _navigateToHome.emit(true)
            dismissLoading()
        }
    }
}