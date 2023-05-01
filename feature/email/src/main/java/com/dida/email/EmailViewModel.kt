package com.dida.email

import com.dida.common.base.BaseViewModel
import com.dida.data.model.NotCorrectPasswordException
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

    private val _retryEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val retryEvent: SharedFlow<Unit> = _retryEvent

    val userInputState: MutableStateFlow<String> = MutableStateFlow<String>("")
    var verifyNumberValue = ""

    // 비밀번호 받아오기
    fun getSendEmail() {
        baseViewModelScope.launch {
            showLoading()
            sendEmailAPI()
                .onSuccess {
                    verifyNumberValue = it.random
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    fun verifyNumberCheck() : Boolean{
        return userInputState.value == verifyNumberValue
    }

    private val _createWalletState = MutableStateFlow<Boolean>(false)
    val createWalletState: StateFlow<Boolean> = _createWalletState

    fun postCreateWallet(password: String, passwordCheck: String) {
        baseViewModelScope.launch {
            showLoading()
            createWalletAPI(password, passwordCheck)
                .onSuccess {
                    _createWalletState.value = true
                    dismissLoading() }
                .onError { e ->
                    when(e) {
                        is NotCorrectPasswordException -> _retryEvent.emit(Unit)
                        else -> catchError(e)
                    }
                }
        }
    }

    var timeState: MutableStateFlow<String> = MutableStateFlow<String>("")

    fun timeToString(minute: Int, second: Int) {
        if(second>=10) { timeState.value = "0$minute:$second" }
        else { timeState.value = "0$minute:0$second" }
    }
}
