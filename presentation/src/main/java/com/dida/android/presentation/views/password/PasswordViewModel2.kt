package com.dida.android.presentation.views.password

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.util.AppLog
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckPasswordAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel2 @Inject constructor(
    private val checkPasswordAPI: CheckPasswordAPI
) : BaseViewModel() {

    private val stack = Stack<Int>()

    private fun checkPassword() {
        baseViewModelScope.launch {
            val password = "000000"
            checkPasswordAPI(password)
                .onSuccess {  }
                .onError {  }
        }
    }
    fun clearStack(){
        stack.clear()
        //TODO : 잠깐 FLag줘서 빨간색으로 만들기
    }

    fun addStack(num : Int){
        if(stack.size<7){
            stack.push(num)
        }
        if(stack.size==6){
            //TODO : 완료신호
            AppLog.d(stackToString())
        }
    }

    fun removeStack(){
        if(!stack.isEmpty()){
            stack.pop()
        }
    }

    fun stackToString() : String {
        var password = ""
        stack.forEach {
            password += it.toString()
        }
        return password
    }
}