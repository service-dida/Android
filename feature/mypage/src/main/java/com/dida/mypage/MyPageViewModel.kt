package com.dida.mypage

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.domain.model.main.UserNft
import com.dida.domain.model.main.UserProfile
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostLikeAPI
import com.dida.domain.usecase.main.UserNftAPI
import com.dida.domain.usecase.main.UserProfileAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userProfileAPI: UserProfileAPI,
    private val postLikeAPI: PostLikeAPI,
    userNftAPI: UserNftAPI,
) : BaseViewModel(), MypageActionHandler, NftActionHandler {

    private val TAG = "MyPageViewModel"

    private val _navigationEvent: MutableSharedFlow<MypageNavigationAction> = MutableSharedFlow<MypageNavigationAction>()
    val navigationEvent: SharedFlow<MypageNavigationAction> = _navigationEvent

    private val _myPageState: MutableStateFlow<UiState<UserProfile>> = MutableStateFlow(UiState.Loading)
    val myPageState: StateFlow<UiState<UserProfile>> = _myPageState

    private val hasWalletState = MutableStateFlow<Boolean>(false)

    val userCardState: Flow<PagingData<UserNft>> = createUserCardPager(userNftAPI = userNftAPI)
        .flow.cachedIn(baseViewModelScope)

    enum class CardSortType{
        NEWEST, OLDEST
    }

    private val _cardSortTypeState: MutableStateFlow<CardSortType> = MutableStateFlow<CardSortType>(CardSortType.NEWEST)
    val cardSortTypeState: StateFlow<CardSortType> = _cardSortTypeState

    fun getUserInfo() {
        baseViewModelScope.launch {
            userProfileAPI().onSuccess {
                delay(SHIMMER_TIME)
                _myPageState.value = UiState.Success(it)
                hasWalletState.value = it.getWallet
            }.onError { e -> catchError(e) }
        }
    }

    private fun setCardSort(type: CardSortType) {
        when(type) {
            CardSortType.NEWEST -> {}
            CardSortType.OLDEST -> {}
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
                    _navigationEvent.emit(MypageNavigationAction.NavigateToLikeButtonClicked)
                    dismissLoading()
                }.onError { e -> catchError(e) }
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
