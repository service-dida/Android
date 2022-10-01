package com.dida.android.presentation.views.nav.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.klaytn.UploadAssetUsecase
import com.dida.domain.usecase.main.MintNftAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AddPurposeViewModel @Inject constructor(
    private val mintNftAPI: MintNftAPI,
    private val uploadAssetUsecase: UploadAssetUsecase,
) : BaseViewModel() {

    private val TAG = "AddPurposeViewModel"

    private val _nftImageLiveData = MutableLiveData<String>()
    val nftImageLiveData: LiveData<String> = _nftImageLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String> = _titleLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String> = _descriptionLiveData

    private val _successCreateNft: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val successCreateNft: SharedFlow<Boolean> = _successCreateNft

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

    fun uploadAsset(file : File){
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModelScope.launch {
            uploadAssetUsecase(requestBody)
                .onSuccess { mintNFT(it.uri) }
                .onError { e -> catchError(e) }
        }
    }

    fun mintNFT(uri : String){
        viewModelScope.launch {
            mintNftAPI(titleLiveData.value.toString(),descriptionLiveData.value.toString(),uri)
                .onSuccess { _successCreateNft.emit(true) }
                .onError { e -> catchError(e) }
        }
    }
}