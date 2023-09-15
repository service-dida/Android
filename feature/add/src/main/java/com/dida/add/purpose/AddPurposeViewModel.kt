package com.dida.add.purpose

import com.dida.common.base.BaseViewModel
import com.dida.domain.flatMap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.klaytn.UploadAssetUseCase
import com.dida.domain.usecase.CancelSellNftUseCase
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.CreateNftUseCase
import com.dida.domain.usecase.SellNftUseCase
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
    private val createNftUseCase: CreateNftUseCase,
    private val uploadAssetUseCase: UploadAssetUseCase,
    private val profileUseCase: CommonProfileUseCase,
    private val sellNftUseCase: SellNftUseCase
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

    // TODO: NFT 바로 판매하는 로직 서버 Response 수정 필요
    fun mintNFT(password: String , type : AddNftType, price: Double) {
        baseViewModelScope.launch {
            showLoading()

            val file = File(nftImageState.value)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

            uploadAssetUseCase(requestBody)
                .onSuccess { }
                .onError { e -> catchError(e) }
                .flatMap {
                    createNftUseCase(password, titleState.value, descriptionState.value, it.uri)
                }
                .onSuccess { cardId ->
                    if (type == AddNftType.NOT_SALE) _navigationEvent.emit(AddPurposeNavigationAction.NavigateToMyPage)
//                    else sellNft(password, cardId, price)
                }
                .onError { e -> catchError(e) }
        }
    }

    private fun sellNft(payPwd : String, cardId: Long, price : Double){
        baseViewModelScope.launch {
            sellNftUseCase(payPwd, cardId, price)
                .onSuccess {
                    dismissLoading()
                    _navigationEvent.emit(AddPurposeNavigationAction.NavigateToMyPage)
                }
                .onError { e -> catchError(e) }
        }
    }
}
