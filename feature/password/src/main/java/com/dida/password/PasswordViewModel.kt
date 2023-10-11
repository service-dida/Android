package com.dida.password

import android.os.Build
import androidx.annotation.RequiresApi
import com.dida.common.base.BaseViewModel
import com.dida.common.util.AppLog
import com.dida.common.util.SecurityUtil2
import com.dida.data.model.login.GetPublicKeyResponse
import com.dida.domain.flatMap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.BuyNftUseCase
import com.dida.domain.usecase.CheckPasswordUseCase
import com.dida.domain.usecase.PatchPasswordUseCase
import com.dida.domain.usecase.PublicKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rsaEncode
import java.util.Stack
import javax.inject.Inject

// TODO : 비밀번호 확인 API 추가하기
@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val getPublicKeyUseCase: PublicKeyUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase
) : BaseViewModel() {

    private var isClickable = true

    private val stack = Stack<Int>()
    private var stackSize = 0
    private var settingYn = false

    private val _completeEvent: MutableSharedFlow<String> = MutableSharedFlow<String>()
    val completeEvent: SharedFlow<String> = _completeEvent

    private val _failEvent: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val failEvent: SharedFlow<Boolean> = _failEvent

    private val _dismissEvent: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val dismissEvent: SharedFlow<Boolean> = _dismissEvent

    private val _stackSizeState = MutableStateFlow<Int>(0)
    val stackSizeState: StateFlow<Int> = _stackSizeState

    private val _wrongCountState = MutableStateFlow<String>("")
    val wrongCountState: StateFlow<String> = _wrongCountState

    fun addStack(num: Int) {
        if (isClickable) {
            if (stack.size < stackSize) {
                stack.push(num)
                _stackSizeState.value = stack.size
            }
            if (stack.size == stackSize) {
                submitStack()
            }
        }
    }

    fun removeStack() {
        if (isClickable) {
            if (!stack.isEmpty()) {
                stack.pop()
                _stackSizeState.value = stack.size
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitStack() {
        var password = ""
        stack.forEach {
            password += it.toString()
        }
        baseViewModelScope.launch {
            if (settingYn) {
                _completeEvent.emit(password)
            } else {
                checkPassword(password)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun checkPassword(password: String) {
        baseViewModelScope.launch {
            getPublicKeyUseCase()
                .onSuccess {
                    AppLog.e("haha ${SecurityUtil2().rsaEncode(password,it.publicKey)}")
                    checkPasswordUseCase(SecurityUtil2().rsaEncode(password,it.publicKey))
                        .onSuccess {
                            _completeEvent.emit(password)
                        }.onError { e ->
                            AppLog.e("haha ${e}")
                            //TODO 틀렸을 때
                            /*isClickable = false
                            _wrongCountState.emit("${it.wrongCnt}/5")
                            _failEvent.emit(true)

                            stack.clear()
                            delay(1000)
                            _failEvent.emit(false)
                            isClickable = true*/

                            //TODO 5번 틀렸을 때
                            /*if (e is WrongPassword5TimesException) {
                                _dismissEvent.emit(true)
                            } else {
                                catchError(e)
                            }*/
                        }
                }
                .onError {
                    AppLog.e("haha ${it}")
                }
        }
    }

    fun initPwdInfo(stackSize: Int, settingYn: Boolean) {
        this.stackSize = stackSize
        this.settingYn = settingYn
    }
}
