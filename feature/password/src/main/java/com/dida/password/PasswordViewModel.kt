package com.dida.password

import com.dida.common.base.BaseViewModel
import com.dida.data.model.WrongPassword5TimesException
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckPasswordAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordAPI: CheckPasswordAPI
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

    fun submitStack() {
        var password = ""
        stack.forEach {
            password += it.toString()
        }
        baseViewModelScope.launch {
            if(settingYn){
                _completeEvent.emit(password)
            }else{
                checkPassword(password)
            }
        }
    }

    private suspend fun checkPassword(password : String){
        passwordAPI(password)
            .onSuccess {
                if (it.flag) {
                    _completeEvent.emit(password)
                } else {
                    isClickable = false
                    _failEvent.emit(true)
                    stack.clear()
                    delay(1000)
                    _failEvent.emit(false)
                    isClickable = true
                }
            }
            .onError { e ->
                if (e is WrongPassword5TimesException) {
                    _dismissEvent.emit(true)
                } else {
                    catchError(e)
                }
            }
    }
        fun initPwdInfo(stackSize: Int,settingYn : Boolean) {
            this.stackSize = stackSize
            this.settingYn = settingYn
        }
    }
