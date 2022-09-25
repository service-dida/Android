package com.dida.android.presentation.views.nav.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.repository.MainRepository
import com.dida.domain.usecase.CheckPasswordAPI
import com.dida.domain.usecase.CreateUserAPI
import com.dida.domain.usecase.WalletExistedAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val walletExistedAPI: WalletExistedAPI,
    private val checkPasswordAPI: CheckPasswordAPI,
) : BaseViewModel() {

    private val TAG = "AddViewModel"

    private val _walletExistsLiveData = MutableLiveData<Boolean>()
    val walletExistsLiveData: LiveData<Boolean> = _walletExistsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _nftImageLiveData = MutableLiveData("")
    val nftImageLiveData: LiveData<String> = _nftImageLiveData

    private val _titleLengthLiveData = MutableLiveData(0)
    val titleLengthLiveData: LiveData<Int> = _titleLengthLiveData

    private val _descriptionLengthLiveData = MutableLiveData(0)
    val descriptionLengthLiveData: LiveData<Int> = _descriptionLengthLiveData

    fun getWalletExists() {
        viewModelScope.launch {
            walletExistedAPI()
                .onSuccess {
                    _walletExistsLiveData.postValue(it)
                }.onError {
                    _errorLiveData.postValue(it.message)
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

    fun checkPassword(password: String) {
        viewModelScope.launch {
            checkPasswordAPI(password)
                .onSuccess {
                    _checkPasswordLiveData.postValue(it)
                }.onError {
                    _errorLiveData.postValue(it.message)
                }
        }
    }
}