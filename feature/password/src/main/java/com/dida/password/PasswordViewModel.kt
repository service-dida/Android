package com.dida.password

import com.dida.common.base.BaseViewModel
import com.dida.data.model.Wallet002Exception
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CheckPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val checkPasswordUseCase: CheckPasswordUseCase,
) : BaseViewModel() {

    private var isClickable = true

    private val stack = Stack<Int>()
    private var stackSize = 0
    private var settingYn = false

    private val _completeEvent: MutableSharedFlow<String> = MutableSharedFlow<String>()
    val completeEvent: SharedFlow<String> = _completeEvent

    private val _failEvent: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val failEvent: SharedFlow<Boolean> = _failEvent

    private val _dismissEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val dismissEvent: SharedFlow<Unit> = _dismissEvent

    private val _stackSizeState = MutableStateFlow<Int>(0)
    val stackSizeState: StateFlow<Int> = _stackSizeState

    private val _wrongCountState = MutableStateFlow<Int>(1)
    val wrongCountState: StateFlow<Int> = _wrongCountState

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

    private fun submitStack() {
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

    private suspend fun checkPassword(password : String) {
        checkPasswordUseCase(password)
            .onSuccess {
                _completeEvent.emit(password)
            }.onError { e ->
                when (e) {
                    is Wallet002Exception -> wrongPassword()
                    else -> catchError(e)
                }
            }
    }

    private fun wrongPassword() = baseViewModelScope.launch {
        isClickable = false
        _wrongCountState.emit(wrongCountState.value + 1)
        _failEvent.emit(true)

        stack.clear()
        delay(1000)
        _failEvent.emit(false)
        isClickable = true

        if (wrongCountState.value >= 5) _dismissEvent.emit(Unit)
    }

    fun initPwdInfo(stackSize: Int, settingYn: Boolean) {
        this.stackSize = stackSize
        this.settingYn = settingYn
    }
}
