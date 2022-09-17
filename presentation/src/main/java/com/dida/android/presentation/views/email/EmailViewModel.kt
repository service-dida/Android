package com.dida.android.presentation.views.email

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel() {
    private val TAG = "EmailViewModel"

    private val _sendEmailStateFlow: MutableStateFlow<String?> = MutableStateFlow<String?>(null)
    val sendEmailStateFlow: StateFlow<String?> = _sendEmailStateFlow

    val userInputStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            userInputStateFlow.debounce(100).collect {
                verifyNumberCheck(it)
            }
        }
    }

    // 비밀번호 받아오기
    fun getSendEmail() {
        baseViewModelScope.launch {
            mainUsecase.getSendEmailAPI()
                .onSuccess {
                    _verifyCheck.value = false
                    _sendEmailStateFlow.value = it.random
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    // 비밀번호 체크
    private val _verifyCheck = MutableStateFlow<Boolean>(false)
    val verifyCheck: StateFlow<Boolean> = _verifyCheck

    fun verifyNumberCheck(number: String){
        if(number == _sendEmailStateFlow.value) { _verifyCheck.value = true }
        else { _verifyCheck.value = false }
    }

    private val _createWalletEvent = MutableStateFlow<Boolean>(false)
    val createWalletEvent: StateFlow<Boolean> = _createWalletEvent

    fun postCreateWallet(password: String, passwordCheck: String) {
        baseViewModelScope.launch {
            mainUsecase.postCreateWalletAPI(password, passwordCheck)
                .onSuccess {
                    _createWalletEvent.value = true
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    var timeStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")

    fun timeToString(minute: Int, second: Int) {
        if(second>=10) {
            timeStateFlow.value = "0$minute:$second"
        }
        else {
            timeStateFlow.value = "0$minute:0$second"
        }
    }
}