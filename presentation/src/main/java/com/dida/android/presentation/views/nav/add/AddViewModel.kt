package com.dida.android.presentation.views.nav.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val mainUsecase: MainUsecase) : BaseViewModel() {

    private val TAG = "AddViewModel"

    private val _walletExistsLiveData = MutableLiveData<Boolean>()
    val walletExistsLiveData: LiveData<Boolean>
        get() = _walletExistsLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private val _nftImageLiveData = MutableLiveData<String>("")
    val nftImageLiveData: LiveData<String>
        get() = _nftImageLiveData

    private val _titleLengthLiveData = MutableLiveData<Int>(0)
    val titleLengthLiveData: LiveData<Int>
        get() = _titleLengthLiveData

    private val _descriptionLengthLiveData = MutableLiveData<Int>(0)
    val descriptionLengthLiveData: LiveData<Int>
        get() = _descriptionLengthLiveData

    fun getWalletExists() {
        viewModelScope.launch {
            mainUsecase.getWalletExistsAPI().let {
                if(it != null) {
                    _walletExistsLiveData.postValue(it)
                }
                else{
                    _errorLiveData.postValue(true)
                }
            }
        }
    }

    fun setNFTImage(uri: Uri?){
        _nftImageLiveData.postValue(uri.toString())
    }

    fun setTitleLength(length : Int){
        _titleLengthLiveData.postValue(length)
    }

    fun setDescriptionLength(length : Int){
        _descriptionLengthLiveData.postValue(length)
    }

    private val _checkPasswordLiveData = MutableLiveData<Boolean>()
    val checkPasswordLiveData: LiveData<Boolean>
        get() = _checkPasswordLiveData

    /*
     0 -> 네트워크 오류
     1 -> 비밀번호 맞음
     2 -> 비밀번호 틀림
    */
    fun checkPassword(password: String) {
        viewModelScope.launch {
            mainUsecase.getCheckPasswordAPI(password).let {
                when(it) {
                    1 -> _checkPasswordLiveData.postValue(true)
                    2 -> _checkPasswordLiveData.postValue(false)
                    else -> _errorLiveData.postValue(true)
                }
            }
        }
    }
}