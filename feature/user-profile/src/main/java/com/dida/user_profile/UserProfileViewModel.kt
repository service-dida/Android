package com.dida.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGING_SIZE
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UPDATED_DESC
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.Contents
import com.dida.domain.flatMap
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.Follow
import com.dida.domain.main.model.MemberProfile
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

    enum class CardSortType{
        NEWEST, OLDEST
    }

    private val _cardSortTypeState: MutableStateFlow<CardSortType> = MutableStateFlow<CardSortType>(CardSortType.NEWEST)
    val cardSortTypeState: StateFlow<CardSortType> = _cardSortTypeState.asStateFlow()

    private val _userProfileState: MutableStateFlow<UiState<MemberProfile>> = MutableStateFlow<UiState<MemberProfile>>(UiState.Loading)
    val userProfileState: StateFlow<UiState<MemberProfile>> = _userProfileState.asStateFlow()

    private val _userCardState: MutableStateFlow<Contents<CommonProfileNft>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, content = emptyList())
    )
    val userCardState: StateFlow<Contents<CommonProfileNft>> = _userCardState.asStateFlow()

    fun getUserProfile() {
        _userProfileState.value = UiState.Loading
        baseViewModelScope.launch {
            memberProfileUseCase(memberId = userId)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    setCardSort(type = cardSortTypeState.value)
                    _userProfileState.value = UiState.Success(it)
                }.flatMap {
                    memberProfileNftUseCase(memberId = it.memberDetailInfo.memberInfo.memberId, page = 0, pageSize = PAGING_SIZE, sort = UPDATED_DESC)
                }.onSuccess {
                    _userCardState.value = it
                }
                .onError { e -> catchError(e) }
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
                    if (liked) _messageEvent.emit(UserMessageAction.DeleteCardBookmarkMessage)
                    else _messageEvent.emit(UserMessageAction.AddCardBookmarkMessage)
                    _navigationEvent.emit(UserProfileNavigationAction.NavigateToCardLikeButtonClicked)
                    getUserProfile()
                }.onError { e -> catchError(e) }
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

    override fun onCardSortTypeClicked(type: CardSortType) {
        _cardSortTypeState.value = type
        setCardSort(type = cardSortTypeState.value)
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

    private fun setCardSort(type: CardSortType) {
        when(type) {
            CardSortType.NEWEST -> {}
            CardSortType.OLDEST -> {}
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
