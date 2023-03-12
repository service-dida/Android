package com.dida.user_profile

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.domain.flatMap
import com.dida.domain.model.main.OtherUserProfie
import com.dida.domain.model.main.UserNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostLikeAPI
import com.dida.domain.usecase.main.PostUserFollowAPI
import com.dida.domain.usecase.main.UserCardUserIdAPI
import com.dida.domain.usecase.main.UserUserIdAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val postLikeAPI: PostLikeAPI,
    private val userUserIdAPI: UserUserIdAPI,
    private val postUserFollowAPI: PostUserFollowAPI,
    private val userCardUserIdAPI: UserCardUserIdAPI,
) : BaseViewModel(), UserProfileActionHandler, NftActionHandler {

    private val TAG = "UserProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<UserProfileNavigationAction> = MutableSharedFlow<UserProfileNavigationAction>()
    val navigationEvent: SharedFlow<UserProfileNavigationAction> = _navigationEvent.asSharedFlow()

    enum class CardSortType{
        NEWEST,
        OLDEST
    }
    private val _cardSortTypeState: MutableStateFlow<CardSortType> = MutableStateFlow<CardSortType>(CardSortType.NEWEST)
    val cardSortTypeState: StateFlow<CardSortType> = _cardSortTypeState.asStateFlow()

    private val _userIdState: MutableStateFlow<Long> = MutableStateFlow<Long>(0)
    val userIdState: StateFlow<Long> = _userIdState.asStateFlow()

    private val _userProfileState: MutableStateFlow<UiState<OtherUserProfie>> = MutableStateFlow<UiState<OtherUserProfie>>(UiState.Loading)
    val userProfileState: StateFlow<UiState<OtherUserProfie>> = _userProfileState.asStateFlow()

    private val _userCardState: MutableStateFlow<UiState<List<UserNft>>> = MutableStateFlow<UiState<List<UserNft>>>(UiState.Loading)
    val userCardState: StateFlow<UiState<List<UserNft>>> = _userCardState.asStateFlow()

    fun setUserId(userId: Long) {
        baseViewModelScope.launch {
            _userIdState.value = userId
        }
    }
    fun getUserProfile() {
        baseViewModelScope.launch {
            userUserIdAPI(userId = userIdState.value)
                .onSuccess { _userProfileState.value = UiState.Success(it) }
                .flatMap { userCardUserIdAPI(userId = userIdState.value) }
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _userCardState.value = UiState.Success(it) }
                .onError { e -> catchError(e) }
        }
    }


    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(UserProfileNavigationAction.NavigateToDetailNft(nftId))
        }
    }

    override fun onLikeBtnClicked(nftId: Long) {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(nftId)
                .onSuccess {  }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onFollowClicked() {
        baseViewModelScope.launch {
            showLoading()
            postUserFollowAPI(userId = userIdState.value)
                .onSuccess { getUserProfile() }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCardSortTypeClicked(type: CardSortType) {
        _cardSortTypeState.value = type
    }
}
