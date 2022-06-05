package com.dida.android.presentation.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.GlobalApplication
import com.dida.android.data.repository.MainRepository
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    private val TAG = "LoginMainViewModel"


    /** 카카오 로그인 결과 LiveData
     * -1 : 로그인 실패
     * 0 : 회원가입 필요
     * 1 : 로그인 성공
     */
    private val _kakaoLoginSuccessLiveData = MutableLiveData<Int>()
    val kakaoLoginSuccessLiveData: LiveData<Int>
        get() = _kakaoLoginSuccessLiveData

    private val _kakaoEmailSuccessLiveData = MutableLiveData<String>()
    val kakaoEmailSuccessLiveData: LiveData<String>
        get() = _kakaoEmailSuccessLiveData

    suspend fun loginAPIServer(idToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.loginAPIServer(idToken).let {
                if(it.isSuccessful){
                    if(it.body()?.refreshToken.isNullOrEmpty()){
                        _kakaoEmailSuccessLiveData.postValue(it.body()?.accessToken)
                        _kakaoLoginSuccessLiveData.postValue(0)
                    }else{
                        GlobalApplication.mySharedPreferences.setAccessToken(it.body()?.accessToken, it.body()?.refreshToken)
                        _kakaoLoginSuccessLiveData.postValue(1)
                    }
                }
                else{
                    _kakaoLoginSuccessLiveData.postValue(-1)
                    GlobalApplication.mySharedPreferences.removeAccessToken()
                }
            }
        }


    }
}