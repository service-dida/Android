package com.dida.android.presentation.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import com.dida.domain.model.splash.AppVersionResponse
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
            val version = mainRepository.checkVersionAPI()
            if(version.isSuccessful){
                Log.d(TAG, "checkVersion: ${version.body().toString()}")
                _appVersion.postValue(version.body())
            }else{
                Log.d(TAG, "checkVersion: error")
            }
        }
    }
}