package com.dida.android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.data.repository.MainRepository
import com.dida.android.domain.model.splash.AppVersionResponse
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _appVersion = MutableLiveData<AppVersionResponse>()
    val appVersion: LiveData<AppVersionResponse>
        get() = _appVersion

    fun checkVersion(){
        viewModelScope.launch {
            val version = mainRepository.checkVersion()
            if(version.isSuccessful){
                Log.d(TAG, "checkVersion: ${version.body().toString()}")
                _appVersion.postValue(version.body())
            }else{
                Log.d(TAG, "checkVersion: error")
            }
        }
    }
}