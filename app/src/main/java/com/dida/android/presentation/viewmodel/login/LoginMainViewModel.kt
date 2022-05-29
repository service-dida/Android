package com.dida.android.presentation.viewmodel.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dida.android.GlobalApplication
import com.dida.android.data.repository.MainRepository
import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun loginAPIServer(idToken: String) {
        try {
            mainRepository.loginAPIServer(idToken).enqueue(object : Callback<LoginResponseModel> {
                override fun onResponse(
                    call: Call<LoginResponseModel>,
                    response: Response<LoginResponseModel>
                ) {
                    when {
                        response.isSuccessful -> {
                            response.body()?.let {
                                if(it.refreshToken.isEmpty()){
                                    _kakaoLoginSuccessLiveData.postValue(0)
                                }else{
                                    GlobalApplication.mySharedPreferences.setAccessToken(it.accessToken, it.refreshToken)
                                    _kakaoLoginSuccessLiveData.postValue(1)
                                }
                            }
                        }
                        else -> {
                            _kakaoLoginSuccessLiveData.postValue(-1)
                            GlobalApplication.mySharedPreferences.removeAccessToken()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                    //TODO : 네트워크 에러 처리
                    Log.d(TAG, "onFailure: 네트워크 에러")
                    _kakaoLoginSuccessLiveData.postValue(-1)
                }
            })
        }catch (e : Exception){
            //TODO : 네트워크 에러 처리
            Log.d(TAG, "onFailure: 네트워크 에러")
            _kakaoLoginSuccessLiveData.postValue(-1)
        }

    }
}