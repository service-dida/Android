package com.dida.mypage

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.flatMap
import com.dida.domain.model.main.UserNft
import com.dida.domain.model.main.UserProfile
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostLikeAPI
import com.dida.domain.usecase.main.UserNftAPI
import com.dida.domain.usecase.main.UserProfileAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userProfileAPI: UserProfileAPI,
    private val userNftAPI: UserNftAPI,
    private val postLikeAPI: PostLikeAPI
) : BaseViewModel(), MypageActionHandler, NftActionHandler {

    private val TAG = "MyPageViewModel"

    private val _navigationEvent: MutableSharedFlow<MypageNavigationAction> = MutableSharedFlow<MypageNavigationAction>()
    val navigationEvent: SharedFlow<MypageNavigationAction> = _navigationEvent

    private val _myPageState: MutableStateFlow<UiState<UserProfile>> = MutableStateFlow(UiState.Loading)
    val myPageState: StateFlow<UiState<UserProfile>> = _myPageState

    private val _hasWalletState = MutableStateFlow<Boolean>(false)
    val hasWalletState: StateFlow<Boolean> = _hasWalletState

    private val _hasMyNftState: MutableStateFlow<UiState<List<UserNft>>> =
        MutableStateFlow(UiState.Loading)
    val hasMyNftState: StateFlow<UiState<List<UserNft>>> = _hasMyNftState


    enum class CardSortType{
        NEWEST, OLDEST
    }

    private val _cardSortTypeState: MutableStateFlow<CardSortType> = MutableStateFlow<CardSortType>(CardSortType.NEWEST)
    val cardSortTypeState: StateFlow<CardSortType> = _cardSortTypeState

    fun getMypage() {
        baseViewModelScope.launch {
            userNftAPI()
                .onSuccess {
                    _hasMyNftState.value = UiState.Success(it)
                    setCardSort(type = cardSortTypeState.value)
                }
                .flatMap {
                    userProfileAPI()
                        .onSuccess {
                            delay(SHIMMER_TIME)
                            _myPageState.value = UiState.Success(it)
                            _hasWalletState.value = it.getWallet }
                        .onError { e -> catchError(e) }
                }
                .onError { e -> catchError(e) }
        }
    }

    private fun setCardSort(type: CardSortType) {
        when(type) {
            CardSortType.NEWEST -> _hasMyNftState.value = UiState.Success(hasMyNftState.value.successOrNull()!!.sortedByDescending { it.cardId })
            CardSortType.OLDEST -> _hasMyNftState.value = UiState.Success(hasMyNftState.value.successOrNull()!!.sortedBy { it.cardId })
        }
    }

    override fun onWalletClicked() {
        baseViewModelScope.launch {
            if (hasWalletState.value) {
                _navigationEvent.emit(MypageNavigationAction.NavigateToWallet)
            } else {
                _navigationEvent.emit(MypageNavigationAction.NavigateToEmail)
            }
        }
    }

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToDetailNft(nftId.toLong()))
        }
    }

    override fun onLikeBtnClicked(nftId: Long, liked: Boolean) {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(nftId)
                .onSuccess {
                    getMypage()
                    dismissLoading()}
                .onError { e -> catchError(e) }
        }
    }

    override fun onCreateButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToCreate)
        }
    }

    override fun onMypageNftTypeClicked(type: CardSortType) {
        _cardSortTypeState.value = type
        setCardSort(type)
    }

    override fun onSettingsClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToSettings)
        }
    }
}
