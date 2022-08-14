package com.dida.android.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.R
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.data.repository.MainRepository
import com.dida.domain.model.login.CreateUserRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "NicknameViewModel"

    /*
    0 -> 초기값
    1 -> 8글자 초과일 경우
    2 -> 닉네임이 중복될 경우
    3 -> 닉네임을 사용할 수 있을 경우
    * */
    private val _nickNameCheck = MutableLiveData<String>()
    val nickNameCheck: LiveData<String> = _nickNameCheck

    fun setNicknameVerify(type: Int){
        when(type) {
            1 -> _nickNameCheck.postValue("닉네임은 8글자 이하입니다.")
            2 -> _nickNameCheck.postValue("중복된 닉네임 입니다. ")
            3 -> _nickNameCheck.postValue("사용 가능한 닉네임 입니다.")
            else -> _nickNameCheck.postValue("")
        }

    }

    private val _nickNameSuccessLiveData = MutableLiveData<Boolean>()
    val nickNameSuccessLiveData: LiveData<Boolean>
        get() = _nickNameSuccessLiveData

    fun nicknameAPIServer(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.nicknameAPI(nickName).let {
                if(it.isSuccessful){
                    _nickNameSuccessLiveData.postValue(it.body()!!.used)
                }
                else{
                    _nickNameSuccessLiveData.postValue(true)
                }
            }
        }
    }

    private val _createUserSuccessLiveData = MutableLiveData<Boolean>()
    val createUserSuccessLiveData: LiveData<Boolean>
        get() = _createUserSuccessLiveData

    fun createUserAPIServer(request: CreateUserRequestModel) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.createuserAPI(request).let {
                if(it.isSuccessful){
                    DataApplication.mySharedPreferences.setAccessToken(it.body()?.accessToken, it.body()?.refreshToken)
                    _createUserSuccessLiveData.postValue(true)
                }
                else{
                    _nickNameSuccessLiveData.postValue(false)
                }
            }
        }
    }
}