package com.dida.android.presentation.views.nav.add.addpurpose

import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.klaytn.UploadAssetUsecase
import com.dida.domain.usecase.main.MintNftAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
) : BaseViewModel(), AddPurposeActionHandler {

    private val TAG = "AddPurposeViewModel"

    private val _nftImageState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nftImageState: StateFlow<String> = _nftImageState.asStateFlow()

    private val _titleState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val titleState: StateFlow<String> = _titleState.asStateFlow()

    private val _descriptionState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val descriptionState: StateFlow<String> = _descriptionState.asStateFlow()

    fun initNFTInfo(imgUrl : String, title : String, description : String) {
        baseViewModelScope.launch {
            _nftImageState.value = imgUrl
            _titleState.value = title
            _descriptionState.value = description
        }
    }

    /**
     * 0 -> 초기값
     * 1 -> 소장용
     * 2 -> 판매용
     * */
    private val _isSalesState: MutableStateFlow<Int> = MutableStateFlow(0)
    val isSalesState: StateFlow<Int> = _isSalesState.asStateFlow()

    private val _navigationEvent: MutableSharedFlow<AddPurposeNavigationAction> = MutableSharedFlow<AddPurposeNavigationAction>()
    val navigationEvent: SharedFlow<AddPurposeNavigationAction> = _navigationEvent.asSharedFlow()

    override fun onTypeNotSaleClicked() {
        baseViewModelScope.launch {
            _isSalesState.value = 1
            _navigationEvent.emit(AddPurposeNavigationAction.NavigateToNotSaled)
        }
    }

    override fun onTypeSaleClicked() {
        baseViewModelScope.launch {
            _isSalesState.value = 2
            _navigationEvent.emit(AddPurposeNavigationAction.NavigateToSaled)
        }
    }

    fun uploadAsset(imagePath : String){
        val file = File(imagePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

        baseViewModelScope.launch {
            showLoading()
            uploadAssetUsecase(requestBody)
                .onSuccess {
                    mintNFT(it.uri)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    private fun mintNFT(uri : String){
        baseViewModelScope.launch {
            mintNftAPI(titleState.value, descriptionState.value, uri)
                .onSuccess { _navigationEvent.emit(AddPurposeNavigationAction.NavigateToMyPage) }
                .onError { e -> catchError(e) }
        }
    }
}