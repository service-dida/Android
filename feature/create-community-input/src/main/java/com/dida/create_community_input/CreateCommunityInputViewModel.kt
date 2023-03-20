package com.dida.create_community_input

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.DetailNftAPI
import com.dida.domain.usecase.main.PostCardIdAPI
import com.dida.domain.usecase.main.PostIdAPI
import com.dida.domain.usecase.main.UpdatePostAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityInputViewModel @Inject constructor(
    private val detailNftAPI: DetailNftAPI,
    private val postCardIdAPI: PostCardIdAPI,
    private val postIdAPI: PostIdAPI,
    private val updatePostAPI: UpdatePostAPI
) : BaseViewModel(), CreateCommunityInputActionHandler {

    private val TAG = "CreateCommunityInputViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityInputNavigationAction> = MutableSharedFlow<CreateCommunityInputNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityInputNavigationAction> = _navigationEvent.asSharedFlow()

    val createState: MutableStateFlow<Boolean> = MutableStateFlow(true)

    private val _cardIdState: MutableStateFlow<Long> = MutableStateFlow<Long>(0)

    private val _cardImgState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val cardImgState: StateFlow<String> = _cardImgState.asStateFlow()

    private val _userImgState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val userImgState: StateFlow<String> = _userImgState.asStateFlow()

    private val _nicknameState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nicknameState: StateFlow<String> = _nicknameState.asStateFlow()

    private val _cardTitleState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val cardTitleState: StateFlow<String> = _cardTitleState.asStateFlow()

    val isNewCreate: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val postIdState: MutableStateFlow<Long> = MutableStateFlow<Long>(0)

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
                    if (it.isNotBlank()) _createBtnState.value = true
                }
            }

            launch {
                descriptionState.collect {
                    descriptionLengthState.value = it.length.toString()+"/300"
                    if (it.isNotBlank()) _createBtnState.value = true
                }
            }
        }
    }

    fun isNewCreate(isCreate: Boolean) {
        isNewCreate.value = isCreate
    }

    fun getPostDetail(postId: Long) {
        baseViewModelScope.launch {
            postIdState.value = postId
            postIdAPI(postId = postId)
                .onSuccess {
                    _cardIdState.value = it.cardId.toLong()
                    _cardImgState.value = it.cardImgUrl
                    _cardTitleState.value = it.title
                    _userImgState.value = it.userImgUrl
                    _nicknameState.value = it.userName
                    titleState.value = it.title
                    descriptionState.value = it.content }
        }
    }

    fun getCardDetail(cardId: Long) {
        baseViewModelScope.launch {
            detailNftAPI(cardId = cardId)
                .onSuccess {
                    _cardIdState.value = it.cardId
                    _cardImgState.value = it.imgUrl
                    _cardTitleState.value = it.title
                    _userImgState.value = it.profileUrl
                    _nicknameState.value = it.nickname }
                .onError { e -> catchError(e) }
        }
    }

    override fun onBackButtonClicked() {
        baseViewModelScope.launch {
            if (isNewCreate.value) _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToBack)
        }
    }

    override fun onCreateClicked() {
        baseViewModelScope.launch {
            showLoading()
            if(titleState.value.isNotBlank() && descriptionState.value.isNotBlank()) {
                if (isNewCreate.value) {
                    postCardIdAPI(cardId = _cardIdState.value, title = titleState.value, content = descriptionState.value)
                        .onSuccess { _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToCommunity) }
                        .onError { e -> catchError(e) }
                } else {
                    updatePostAPI(postId = postIdState.value, title = titleState.value, content = descriptionState.value)
                        .onSuccess { _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToCommunity) }
                        .onError { e -> catchError(e) }
                }
            }
            dismissLoading()
        }
    }

}
