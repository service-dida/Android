package com.dida.android.presentation.views.email

import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CreateWalletAPI
import com.dida.domain.usecase.main.SendEmailAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val sendEmailAPI: SendEmailAPI,
    private val createWalletAPI: CreateWalletAPI
    ) : BaseViewModel() {
    private val TAG = "EmailViewModel"

    private val _sendEmailState: MutableStateFlow<String?> = MutableStateFlow<String?>(null)
    val sendEmailState: StateFlow<String?> = _sendEmailState

    val userInputState: MutableStateFlow<String> = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            userInputState.debounce(100).collect {
                verifyNumberCheck(it)
            }
        }
    }

    // 비밀번호 받아오기
    fun getSendEmail() {
        baseViewModelScope.launch {
            sendEmailAPI()
                .onSuccess {
                    _verifyCheckState.value = false
                    _sendEmailState.value = it.random }
                .onError { e -> catchError(e) }
        }
    }

    // 비밀번호 체크
    private val _verifyCheckState = MutableStateFlow<Boolean>(false)
    val verifyCheckState: StateFlow<Boolean> = _verifyCheckState

    fun verifyNumberCheck(number: String){
        if(number == _sendEmailState.value) { _verifyCheckState.value = true }
        else { _verifyCheckState.value = false }
    }

    private val _createWalletState = MutableStateFlow<Boolean>(false)
    val createWalletState: StateFlow<Boolean> = _createWalletState

    fun postCreateWallet(password: String, passwordCheck: String) {
        baseViewModelScope.launch {
            createWalletAPI(password, passwordCheck)
                .onSuccess { _createWalletState.value = true }
                .onError { e -> catchError(e) }
        }
    }

    var timeState: MutableStateFlow<String> = MutableStateFlow<String>("")

    fun timeToString(minute: Int, second: Int) {
        if(second>=10) { timeState.value = "0$minute:$second" }
        else { timeState.value = "0$minute:0$second" }
    }
}