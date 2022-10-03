package com.dida.android.presentation.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckVersionAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionAPI: CheckVersionAPI
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _appVersion = MutableLiveData<AppVersionResponse>()
    val appVersion: LiveData<AppVersionResponse>
        get() = _appVersion

    fun checkVersion(){
        viewModelScope.launch {
            versionAPI()
                .onSuccess {
                    Log.d(TAG, "checkVersion: ${it.version}")
                    _appVersion.postValue(it) }
                .onError { e -> catchError(e) }
        }
    }
}