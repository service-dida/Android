package com.dida.add.purpose

import com.dida.common.base.BaseViewModel
import com.dida.domain.NetworkResult
import com.dida.domain.flatMap
import com.dida.domain.klaytn.UploadAssetUseCase
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.CreateNftUseCase
import com.dida.domain.usecase.PublicKeyUseCase
import com.dida.domain.usecase.SellNftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import encryptWithPublicKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPurposeViewModel @Inject constructor(
    private val createNftUseCase: CreateNftUseCase,
    private val uploadAssetUseCase: UploadAssetUseCase,
    private val profileUseCase: CommonProfileUseCase,
    private val sellNftUseCase: SellNftUseCase,
    private val getPublicKeyUseCase: PublicKeyUseCase,
) : BaseViewModel(), AddPurposeActionHandler {

    private val TAG = "AddPurposeViewModel"

    private val _nftImageState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nftImageState: StateFlow<String> = _nftImageState.asStateFlow()

    private val _titleState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val titleState: StateFlow<String> = _titleState.asStateFlow()

    private val _descriptionState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val descriptionState: StateFlow<String> = _descriptionState.asStateFlow()

    private val _profileImgState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val profileImgState: StateFlow<String> = _profileImgState.asStateFlow()

    private val _nickNameState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nickNameState: StateFlow<String> = _nickNameState.asStateFlow()

    fun initNFTInfo(imgUrl : String, title : String, description : String) {
        baseViewModelScope.launch {
            _nftImageState.value = imgUrl
            _titleState.value = title
            _descriptionState.value = description

            profileUseCase()
                .onSuccess {
                    _profileImgState.value = it.memberInfo.profileImgUrl ?: ""
                    _nickNameState.value = it.memberInfo.memberName }
                .onError { e -> catchError(e) }
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

    enum class AddNftType{
        NOT_SALE,
        SALE
    }

    fun mintLocalImageToNFT(password: String , type : AddNftType, price: Double) {
        showLoading()
        val file = File(nftImageState.value)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        sellNft(requestBody = requestBody, password = password, type = type, price = price)
    }

    fun mintFileToNFT(password: String , type : AddNftType, price: Double, file: File) {
        showLoading()
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        sellNft(requestBody = requestBody, password = password, type = type, price = price)
    }

    private fun sellNft(requestBody: MultipartBody.Part, password: String, type: AddNftType, price: Double) {
        baseViewModelScope.launch {
            val publicKey = (getPublicKeyUseCase() as NetworkResult.Success).data

            uploadAssetUseCase(requestBody).flatMap { asset ->
                createNftUseCase(
                    payPwd = password.encryptWithPublicKey(publicKey.publicKey),
                    title = titleState.value,
                    description = descriptionState.value,
                    image = asset.uri
                )
            }.onSuccess {
                if (type == AddNftType.NOT_SALE) _navigationEvent.emit(AddPurposeNavigationAction.NavigateToMyPage)
            }.flatMap { nftId ->
                sellNftUseCase(
                    payPwd = password.encryptWithPublicKey(publicKey.publicKey),
                    nftId = nftId,
                    price = price
                )
            }.onSuccess { _navigationEvent.emit(AddPurposeNavigationAction.NavigateToMyPage)
            }.onError { e -> catchError(e) }
            dismissLoading()
        }
    }
}
