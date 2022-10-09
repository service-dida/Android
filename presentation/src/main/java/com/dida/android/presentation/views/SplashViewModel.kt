package com.dida.android.presentation.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckVersionAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionAPI: CheckVersionAPI
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _appVersion = MutableSharedFlow<UiState<AppVersionResponse>>()
    val appVersion: SharedFlow<UiState<AppVersionResponse>> = _appVersion

    fun checkVersion(){
        viewModelScope.launch {
            versionAPI()
                .onSuccess { _appVersion.emit(UiState.Success(it)) }
                .onError { e -> catchError(e) }
        }
    }
}