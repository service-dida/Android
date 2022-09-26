package com.dida.android.presentation.views.nav.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.util.AppLog
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.repository.KlaytnRepository
import com.dida.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AddPurposeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val klaytnRepository: KlaytnRepository
) : BaseViewModel() {

    private val TAG = "AddPurposeViewModel"

    private val _nftImageLiveData = MutableLiveData<String>()
    val nftImageLiveData: LiveData<String>
        get() = _nftImageLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = _titleLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String>
        get() = _descriptionLiveData

    private val _mintNFTEvent = MutableStateFlow<Boolean>(false)
    val mintNFTEvent: StateFlow<Boolean> = _mintNFTEvent

    /**
     * 소장용 : 1
     * 판매용 : 2
    * */
    private val _puposeTypeLiveData = MutableLiveData<Int>(0)
    val puposeTypeLiveData: LiveData<Int>
        get() = _puposeTypeLiveData

    fun initNFTInfo(imgUrl : String, title : String, description : String) {
        _nftImageLiveData.postValue(imgUrl)
        _titleLiveData.postValue(title)
        _descriptionLiveData.postValue(description)
    }

    fun changePurposeType(type : Int){
        _puposeTypeLiveData.postValue(type)
    }

    fun uploadAsset(imagePath : String){
        val file = File(imagePath)

        val requestFile = file.asRequestBody(
            "image/*".toMediaTypeOrNull()
        )

        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModelScope.launch {
            klaytnRepository.uploadAsset(requestBody)
                .onSuccess {
                    mintNFT(it.uri)
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    fun mintNFT(uri : String){
        viewModelScope.launch {
            mainRepository.mintNFT(titleLiveData.value.toString(),descriptionLiveData.value.toString(),uri)
                .onSuccess {
                    //TODO : 만들어졌을때의 화면 이동 구현하기
                    _mintNFTEvent.value = true
                }.onError { e ->
                    catchError(e)
                }
        }
    }
}