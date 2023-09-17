package com.dida.create_community_input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.NftDetailUseCase
import com.dida.domain.usecase.PatchPostUseCase
import com.dida.domain.usecase.PostsDetailUseCase
import com.dida.domain.usecase.WritePostUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateCommunityInputViewModel @AssistedInject constructor(
    @Assisted("createdState") val createdState: Boolean,
    private val nftDetailUseCase: NftDetailUseCase,
    private val postsDetailUseCase: PostsDetailUseCase,
    private val writePostUseCase: WritePostUseCase,
    private val patchPostUseCase: PatchPostUseCase
) : BaseViewModel(), CreateCommunityInputActionHandler {

    private val TAG = "CreateCommunityInputViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityInputNavigationAction> = MutableSharedFlow<CreateCommunityInputNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityInputNavigationAction> = _navigationEvent.asSharedFlow()

    private val _cardIdState: MutableStateFlow<Long> = MutableStateFlow<Long>(0)

    private val _cardImgState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val cardImgState: StateFlow<String> = _cardImgState.asStateFlow()

    private val _userImgState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val userImgState: StateFlow<String> = _userImgState.asStateFlow()

    private val _nicknameState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nicknameState: StateFlow<String> = _nicknameState.asStateFlow()

    private val _cardTitleState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val cardTitleState: StateFlow<String> = _cardTitleState.asStateFlow()

    private val postIdState: MutableStateFlow<Long> = MutableStateFlow<Long>(0)

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

    fun getPostDetail(postId: Long) {
        baseViewModelScope.launch {
            postIdState.value = postId
            postsDetailUseCase(postId = postId)
                .onSuccess {
                    _cardIdState.value = it.nftInfo.nftId
                    _cardImgState.value = it.nftInfo.nftImgUrl
                    _cardTitleState.value = it.nftInfo.nftName
                    _userImgState.value = it.memberInfo.profileImgUrl ?: ""
                    _nicknameState.value = it.memberInfo.memberName
                    titleState.value = it.postInfo.title
                    descriptionState.value = it.postInfo.content
                }.onError { e -> catchError(e) }
        }
    }

    fun getCardDetail(cardId: Long) {
        baseViewModelScope.launch {
            nftDetailUseCase(nftId = cardId)
                .onSuccess {
                    _cardIdState.value = it.nftInfo.nftId
                    _cardImgState.value = it.nftInfo.nftImgUrl
                    _cardTitleState.value = it.nftInfo.nftName
                    _userImgState.value = it.memberInfo.profileImgUrl ?: ""
                    _nicknameState.value = it.memberInfo.memberName
                }
                .onError { e -> catchError(e) }
        }
    }

    override fun onBackButtonClicked() {
        baseViewModelScope.launch {
            if (createdState) _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToBack)
        }
    }

    override fun onCreateClicked() {
        baseViewModelScope.launch {
            showLoading()
            if(titleState.value.isNotBlank() && descriptionState.value.isNotBlank()) {
                if (createdState) {
                    writePostUseCase(nftId = _cardIdState.value, title = titleState.value, content = descriptionState.value)
                        .onSuccess { _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToCommunity) }
                        .onError { e -> catchError(e) }
                } else {
                    patchPostUseCase(postId = postIdState.value, title = titleState.value, content = descriptionState.value)
                        .onSuccess { _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToCommunity) }
                        .onError { e -> catchError(e) }
                }
            }
            dismissLoading()
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("createdState") isCreated: Boolean
        ): CreateCommunityInputViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            createdState: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(createdState) as T
            }
        }
    }

}
