package com.dida.android.presentation.views.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PasswordReconfirmViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val passwordStack = Stack<Int>()

    private val _getWalletLiveData = MutableLiveData<Stack<Int>>(passwordStack)
    val getWalletLiveData: LiveData<Stack<Int>>
        get() = _getWalletLiveData
    private val getWalletValue: Stack<Int>
        get() = getWalletLiveData.value!!

    private val _completeLiveData = MutableLiveData<Boolean>()
    val completeLiveData: LiveData<Boolean>
        get() = _completeLiveData

    fun clearStack(){
        val stack = getWalletValue
        stack.clear()
        _getWalletLiveData.postValue(stack)
    }

    fun addStack(num : Int){
        val stack = getWalletValue
        if(stack.size<7){
            stack.push(num)
            _getWalletLiveData.postValue(stack)
        }

        if(stack.size==6){
            _completeLiveData.postValue(true)
        }
    }
    fun removeStack(){
        if(!getWalletValue.isEmpty()){
            val stack = getWalletValue
            stack.pop()
            _getWalletLiveData.postValue(stack)
        }
    }

    fun stackToString() : String {
        val stack = getWalletValue
        var password = ""
        stack.forEach {
            password += it.toString()
        }
        return password
    }
}