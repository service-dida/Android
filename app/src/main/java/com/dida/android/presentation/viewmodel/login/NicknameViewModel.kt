package com.dida.android.presentation.viewmodel.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.GlobalApplication
import com.dida.android.data.repository.MainRepository
import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.domain.model.login.NicknameResponseModel
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "NicknameViewModel"

    private val _nickNameSuccessLiveData = MutableLiveData<Boolean>()
    val nickNameSuccessLiveData: LiveData<Boolean>
        get() = _nickNameSuccessLiveData

    fun nicknameAPIServer(nickName: String) {
        try {
            mainRepository.nicknamePIServer(nickName).enqueue(object : Callback<NicknameResponseModel> {
                override fun onResponse(
                    call: Call<NicknameResponseModel>,
                    response: Response<NicknameResponseModel>,
                ) {
                    when {
                        response.isSuccessful -> {
                            response.body()?.let {
                                _nickNameSuccessLiveData.postValue(it.used)
                            }
                        }
                        else -> {
                            _nickNameSuccessLiveData.postValue(true)
                        }
                    }
                }
                override fun onFailure(call: Call<NicknameResponseModel>, t: Throwable) {
                    _nickNameSuccessLiveData.postValue(true)
                }
            })
        }catch (e : Exception){
            //TODO : 네트워크 에러 처리
            Log.d(TAG, "onFailure: 네트워크 에러")
            _nickNameSuccessLiveData.postValue(true)
        }

    }

//    fun nicknameAPIServer(nickName: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            mainRepository.nicknamePIServer(nickName).let {
//                if(it.isSuccessful){
//                    _nickNameSuccessLiveData.postValue(it.body()?.used ?: true)
//                }
//                else{
//                    _nickNameSuccessLiveData.postValue(true)
//                }
//            }
//        }
//    }


}