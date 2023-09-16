package com.dida.email

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CreateWalletUseCase
import com.dida.domain.usecase.EmailAuthUseCase
import com.dida.domain.usecase.PatchPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val emailAuthUseCase: EmailAuthUseCase,
    private val createWalletUseCase: CreateWalletUseCase,
    private val patchPasswordUseCase: PatchPasswordUseCase,
) : BaseViewModel() {

    private val TAG = "EmailViewModel"

    private val _navigationEvent: MutableSharedFlow<EmailNavigationAction> = MutableSharedFlow<EmailNavigationAction>()
    val navigationEvent: SharedFlow<EmailNavigationAction> = _navigationEvent.asSharedFlow()

    private val _sendEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val sendEvent: SharedFlow<Unit> = _sendEvent

    private val _retryEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val retryEvent: SharedFlow<Unit> = _retryEvent

    val userInputState: MutableStateFlow<String> = MutableStateFlow<String>("")
    var verifyNumberValue = ""

    // 비밀번호 받아오기
    fun getSendEmail() {
        baseViewModelScope.launch {
            showLoading()
            emailAuthUseCase()
                .onSuccess {
                    verifyNumberValue = it
                    _sendEvent.emit(Unit)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    fun verifyNumberCheck() : Boolean{
        return userInputState.value == verifyNumberValue
    }


    // TODO : 비밀번호 오류 체크하기
    fun postCreateWallet(password: String, passwordCheck: String) {
        baseViewModelScope.launch {
            showLoading()
            createWalletUseCase(password, passwordCheck)
                .onSuccess {
                    _navigationEvent.emit(EmailNavigationAction.SuccessCreateWallet)
                    dismissLoading() }
                .onError { e ->
                    when(e) {
//                        is NotCorrectPasswordException -> _retryEvent.emit(Unit)
                        else -> catchError(e)
                    }
                }
        }
    }

    fun changePassword(nowPwd : String, checkPwd : String){
        baseViewModelScope.launch {
            showLoading()
            patchPasswordUseCase(nowPwd = nowPwd, changePwd = checkPwd)
                .onSuccess { _navigationEvent.emit(EmailNavigationAction.SuccessResetPassword) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }
    var timeState: MutableStateFlow<String> = MutableStateFlow<String>("")

    fun timeToString(minute: Int, second: Int) {
        if(second>=10) { timeState.value = "0$minute:$second" }
        else { timeState.value = "0$minute:0$second" }
    }
}
