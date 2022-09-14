package com.dida.android.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(private val mainRepositoryImpl: MainRepositoryImpl) : BaseViewModel() {

    private val TAG = "NicknameViewModel"

    /*
    0 -> 초기값
    1 -> 8글자 초과일 경우
    2 -> 닉네임이 중복될 경우
    3 -> 닉네임을 사용할 수 있을 경우
    */
    private val _nickNameCheck = MutableLiveData("")
    val nickNameCheck: LiveData<String> = _nickNameCheck

    private val _errorLiveDate = MutableLiveData("")
    val errorLiveDate: LiveData<String> = _errorLiveDate

    fun setNicknameVerify(type: Int){
        when(type) {
            1 -> _nickNameCheck.postValue("닉네임은 8글자 이하입니다.")
            2 -> _nickNameCheck.postValue("중복된 닉네임 입니다.")
            3 -> _nickNameCheck.postValue("사용 가능한 닉네임 입니다.")
            else -> _nickNameCheck.postValue("")
        }
    }

    private val _nickNameSuccessLiveData = MutableLiveData<Boolean>()
    val nickNameSuccessLiveData: LiveData<Boolean>
        get() = _nickNameSuccessLiveData

    fun nicknameAPIServer(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepositoryImpl.nicknameAPI(nickName)
                .onSuccess {
                    _nickNameSuccessLiveData.postValue(it.used)
                }.onError {
                    _nickNameSuccessLiveData.postValue(true)
                    _errorLiveDate.postValue(it.message)
                }
        }
    }

    private val _createUserSuccessLiveData = MutableLiveData<Boolean>()
    val createUserSuccessLiveData: LiveData<Boolean>
        get() = _createUserSuccessLiveData

    fun createUserAPIServer(email: String, nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepositoryImpl.createUserAPI(email, nickName)
                .onSuccess {
                    DataApplication.mySharedPreferences.setAccessToken(it.accessToken, it.refreshToken)
                    _createUserSuccessLiveData.postValue(true)
                }.onError {
                    _errorLiveDate.postValue(it.message)
                }
        }
    }
}