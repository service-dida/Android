package com.dida.android.presentation.views.password

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.views.nav.mypage.MypageNavigationAction
import com.dida.android.util.AppLog
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckPasswordAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class InputNumberViewModel @Inject constructor() : BaseViewModel() {

    private val stack = Stack<Int>()
    private var stackSize = 0

    private val _completeEvent: MutableSharedFlow<String> = MutableSharedFlow<String>()
    val completeEvent: SharedFlow<String> = _completeEvent

    private val _stackSizeState = MutableStateFlow<Int>(0)
    val stackSizeState: StateFlow<Int> = _stackSizeState

    fun clearStack(){
        stack.clear()
        //TODO : 잠깐 FLag줘서 빨간색으로 만들기
    }

    fun addStack(num : Int){
        if(stack.size<stackSize){
            stack.push(num)
            _stackSizeState.value = stack.size
        }
        if(stack.size==stackSize){
            submitStack()
        }
    }

    fun removeStack(){
        if(!stack.isEmpty()){
            stack.pop()
            _stackSizeState.value = stack.size
        }
    }

    fun submitStack() {
        var password = ""
        stack.forEach {
            password += it.toString()
        }
        baseViewModelScope.launch {
            _completeEvent.emit(password)
        }
    }

    fun setStackSize(size : Int){
        stackSize = size
    }
}