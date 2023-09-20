package com.dida.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.Contents
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.Follow
import com.dida.domain.main.model.MemberProfile
import com.dida.domain.main.model.Sort
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MemberFollowUseCase
import com.dida.domain.usecase.MemberProfileNftUseCase
import com.dida.domain.usecase.MemberProfileUseCase
import com.dida.domain.usecase.NftLikeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserProfileViewModel @AssistedInject constructor(
    @Assisted("userId") val userId: Long,
    private val nftLikeUseCase: NftLikeUseCase,
    private val memberProfileUseCase: MemberProfileUseCase,
    private val memberFollowUseCase: MemberFollowUseCase,
    private val memberProfileNftUseCase: MemberProfileNftUseCase,
) : BaseViewModel(), UserProfileActionHandler, NftActionHandler {

    private val TAG = "UserProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<UserProfileNavigationAction> = MutableSharedFlow<UserProfileNavigationAction>()
    val navigationEvent: SharedFlow<UserProfileNavigationAction> = _navigationEvent.asSharedFlow()

    private val _messageEvent: MutableSharedFlow<UserMessageAction> = MutableSharedFlow<UserMessageAction>()
    val messageEvent: SharedFlow<UserMessageAction> = _messageEvent.asSharedFlow()

    private val _cardSortTypeState: MutableStateFlow<Sort> = MutableStateFlow<Sort>(Sort.NEWEST)
    val cardSortTypeState: StateFlow<Sort> = _cardSortTypeState.asStateFlow()

    private val _userProfileState: MutableStateFlow<UiState<MemberProfile>> = MutableStateFlow<UiState<MemberProfile>>(UiState.Loading)
    val userProfileState: StateFlow<UiState<MemberProfile>> = _userProfileState.asStateFlow()

    private val _userCardState: MutableStateFlow<Contents<CommonProfileNft>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, content = emptyList())
    )
    val userCardState: StateFlow<Contents<CommonProfileNft>> = _userCardState.asStateFlow()

    init {
        getUserProfile()
        getUserNfts()
    }

    fun getUserProfile() {
        _userProfileState.value = UiState.Loading
        baseViewModelScope.launch {
            memberProfileUseCase(memberId = userId)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _userProfileState.value = UiState.Success(it)
                }.onError { e -> catchError(e) }
        }
    }

    fun getUserNfts() {
        baseViewModelScope.launch {
            memberProfileNftUseCase(memberId = userId, page = 0, pageSize = PAGE_SIZE, sort = cardSortTypeState.value)
                .onSuccess { _userCardState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun onNextPage() {
        baseViewModelScope.launch {
            if (!userCardState.value.hasNext) return@launch
            memberProfileNftUseCase(memberId = userId, page = userCardState.value.page + 1, pageSize = PAGE_SIZE, sort = cardSortTypeState.value)
                .onSuccess {
                    it.content = (userCardState.value.content.toMutableList()) + it.content
                    _userCardState.value = it
                }.onError { e -> catchError(e) }
        }
    }


    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(UserProfileNavigationAction.NavigateToDetailNft(nftId))
        }
    }

    override fun onLikeBtnClicked(nftId: Long, liked: Boolean) {
        baseViewModelScope.launch {
            showLoading()
            nftLikeUseCase(nftId)
                .onSuccess {
                    _messageEvent.emit(UserMessageAction.NftBookmarkMessage(!liked))
                    _navigationEvent.emit(UserProfileNavigationAction.NavigateToNftUpdate(nftId))
                }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCreateButtonClicked() {}

    override fun onFollowClicked() {
        baseViewModelScope.launch {
            showLoading()
            memberFollowUseCase(memberId = userId)
                .onSuccess {
                    val profile = userProfileState.value.successOrNull()!!
                    if (profile.followed) _messageEvent.emit(UserMessageAction.UserUnFollowMessage)
                    else _messageEvent.emit(UserMessageAction.UserFollowMessage(profile.memberDetailInfo.memberInfo.memberName))
                    getUserProfile()
                }.onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCardSortTypeClicked(type: Sort) {
        _cardSortTypeState.value = type
        getUserNfts()
    }

    override fun onUserFollowerClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(UserProfileNavigationAction.NavigateToUserFollowed(userId = userId, type = Follow.FOLLOWER))
        }
    }

    override fun onUserFollowingClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(UserProfileNavigationAction.NavigateToUserFollowed(userId = userId, type = Follow.FOLLOWING))
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("userId") userId: Long
        ): UserProfileViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            userId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId) as T
            }
        }
    }
}
