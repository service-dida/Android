package com.dida.android.presentation.views.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel() {
    private val TAG = "EmailViewModel"

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private val _sendEmailLiveData = MutableLiveData<String>()
    val sendEmailLiveData: LiveData<String>
        get() = _sendEmailLiveData

    // 비밀번호 받아오기
    fun getSendEmail() {
        viewModelScope.launch {
            mainUsecase.getSendEmailAPI().let {
                if(it != null) {
                    _sendEmailLiveData.postValue(it.random)
                }
                else{
                    _errorLiveData.postValue(true)
                }
            }
        }
    }

    // 비밀번호 체크
    private val _verifyCheck = MutableLiveData<Boolean>(false)
    val verifyCheck: LiveData<Boolean> = _verifyCheck

    fun verifyNumberCheck(number: String){
        if(number == _sendEmailLiveData.value) {
            _verifyCheck.postValue(true)
        }
        else{
            _verifyCheck.postValue(false)
        }
    }

    private val _createWalletLiveData = MutableLiveData<Boolean>()
    val createWalletLiveData: LiveData<Boolean>
        get() = _createWalletLiveData

    fun postCreateWallet(password: String, passwordCheck: String) {
        viewModelScope.launch {
            mainUsecase.postCreateWalletAPI(password, passwordCheck).let {
                if(it) {
                    _createWalletLiveData.postValue(it)
                }
                else {
                    _errorLiveData.postValue(true)
                }
            }
        }
    }
}