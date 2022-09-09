package com.dida.android.presentation.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.splash.AppVersionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val mainRepositoryImpl: MainRepositoryImpl) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _appVersion = MutableLiveData<AppVersionResponse>()
    val appVersion: LiveData<AppVersionResponse>
        get() = _appVersion

    fun checkVersion(){
        viewModelScope.launch {
            val version = mainRepositoryImpl.checkVersionAPI()
            if(version.isSuccessful){
                Log.d(TAG, "checkVersion: ${version.body().toString()}")
                _appVersion.postValue(version.body())
            }else{
                Log.d(TAG, "checkVersion: error")
            }
        }
    }
}