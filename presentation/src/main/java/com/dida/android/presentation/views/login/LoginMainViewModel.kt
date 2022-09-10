package com.dida.android.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication.Companion.mySharedPreferences
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(private val mainRepositoryImpl: MainRepositoryImpl) :
    BaseViewModel() {

    private val TAG = "LoginMainViewModel"


    /** 카카오 로그인 결과 LiveData
     * -1 : 로그인 실패
     * 0 : 회원가입 필요
     * 1 : 로그인 성공
     */
    private val _kakaoLoginSuccessLiveData = MutableLiveData<Int>()
    val kakaoLoginSuccessLiveData: LiveData<Int> = _kakaoLoginSuccessLiveData

    private val _kakaoEmailSuccessLiveData = MutableLiveData<String>()
    val kakaoEmailSuccessLiveData: LiveData<String> = _kakaoEmailSuccessLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    suspend fun loginAPIServer(idToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepositoryImpl.loginAPI(idToken)
                .onSuccess {
                    if(it.refreshToken.isNullOrEmpty()) {
                        _kakaoEmailSuccessLiveData.postValue(it?.accessToken)
                        _kakaoLoginSuccessLiveData.postValue(0)
                    }
                    else {
                        mySharedPreferences.setAccessToken(it?.accessToken, it?.refreshToken)
                        _kakaoLoginSuccessLiveData.postValue(1)
                    }
                }.onError {
                    _kakaoLoginSuccessLiveData.postValue(-1)
                    _errorLiveData.postValue(it.message)
                    mySharedPreferences.removeAccessToken()
                }
        }
    }
}