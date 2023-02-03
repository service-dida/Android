package com.dida.create_community_input

import com.dida.common.base.BaseViewModel
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.DetailNftAPI
import com.dida.domain.usecase.main.PostCardIdAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityInputViewModel @Inject constructor(
    private val detailNftAPI: DetailNftAPI,
    private val postCardIdAPI: PostCardIdAPI,
) : BaseViewModel(), CreateCommunityInputActionHandler {

    private val TAG = "CreateCommunityInputViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityInputNavigationAction> = MutableSharedFlow<CreateCommunityInputNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityInputNavigationAction> = _navigationEvent.asSharedFlow()

    private val _currentNftState: MutableStateFlow<DetailNFT?> = MutableStateFlow<DetailNFT?>(null)
    val currentNftState: StateFlow<DetailNFT?> = _currentNftState.asStateFlow()

    val _isCreateState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)

    private val _createBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val createBtnState: StateFlow<Boolean> = _createBtnState.asStateFlow()

    val titleState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val titleLengthState: MutableStateFlow<String> = MutableStateFlow<String>("0/20")

    val descriptionState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val descriptionLengthState: MutableStateFlow<String> = MutableStateFlow<String>("0/300")

    init {
        baseViewModelScope.launch {
            launch {
                titleState.collect {
                    titleLengthState.value = it.length.toString()+"/20"
                    if(it.isNotBlank()) _createBtnState.value = true
                }
            }

            launch {
                descriptionState.collect {
                    descriptionLengthState.value = it.length.toString()+"/300"
                    if(it.isNotBlank()) _createBtnState.value = true
                }
            }
        }
    }

    fun setCreateState(isCreate: Boolean) {
        _isCreateState.value = isCreate
    }

    fun getCardDetail(cardId: Long) {
        baseViewModelScope.launch {
            detailNftAPI(cardId = cardId)
                .onSuccess { _currentNftState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onBackButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToBack)
        }
    }

    override fun onCreateClicked() {
        baseViewModelScope.launch {
            showLoading()
            if(titleState.value.isNotBlank() && descriptionState.value.isNotBlank()) {
                postCardIdAPI(cardId = currentNftState.value!!.cardId, title = titleState.value, content = descriptionState.value)
                    .onSuccess { _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToCommunity) }
                    .onError { e -> catchError(e) }
            }
            dismissLoading()
        }
    }

}
