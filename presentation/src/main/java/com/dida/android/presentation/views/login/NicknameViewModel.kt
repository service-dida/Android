package com.dida.android.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "NicknameViewModel"

    private val _nickNameSuccessLiveData = MutableLiveData<Boolean>()
    val nickNameSuccessLiveData: LiveData<Boolean>
        get() = _nickNameSuccessLiveData

    fun nicknameAPIServer(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.nicknameAPIServer(nickName).let {
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
            mainRepository.createUserAPIServer(request).let {
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