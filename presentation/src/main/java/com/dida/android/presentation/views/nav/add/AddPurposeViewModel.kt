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
class AddPurposeViewModel @Inject constructor(
    private val mainUsecase: MainUsecase) : BaseViewModel() {

    private val TAG = "AddPurposeViewModel"

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private val _nftImageLiveData = MutableLiveData<String>()
    val nftImageLiveData: LiveData<String>
        get() = _nftImageLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = _titleLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String>
        get() = _descriptionLiveData

    fun initNFTInfo(imgUrl : String, title : String, description : String) {
        _nftImageLiveData.postValue(imgUrl)
        _titleLiveData.postValue(title)
        _descriptionLiveData.postValue(description)
    }
}