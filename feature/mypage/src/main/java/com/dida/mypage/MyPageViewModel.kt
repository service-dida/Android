package com.dida.mypage

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.Sort
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.Contents
import com.dida.domain.flatMap
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CheckWalletUseCase
import com.dida.domain.usecase.CommonProfileNftUseCase
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.NftLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val commonProfileUseCase: CommonProfileUseCase,
    private val checkWalletUseCase: CheckWalletUseCase,
    private val commonProfileNftUseCase: CommonProfileNftUseCase,
    private val nftLikeUseCase: NftLikeUseCase,
) : BaseViewModel(), MypageActionHandler, NftActionHandler {

    private val TAG = "MyPageViewModel"

    private val _navigationEvent: MutableSharedFlow<MypageNavigationAction> = MutableSharedFlow<MypageNavigationAction>()
    val navigationEvent: SharedFlow<MypageNavigationAction> = _navigationEvent

    private val _myPageState: MutableStateFlow<UiState<CommonProfile>> = MutableStateFlow(UiState.Loading)
    val myPageState: StateFlow<UiState<CommonProfile>> = _myPageState

    private val hasWalletState = MutableStateFlow<Boolean>(false)

    private val _userCardState: MutableStateFlow<Contents<CommonProfileNft>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, content = emptyList())
    )
    val userCardState: StateFlow<Contents<CommonProfileNft>> = _userCardState.asStateFlow()

    enum class CardSortType{
        NEWEST, OLDEST
    }

    private val _cardSortTypeState: MutableStateFlow<CardSortType> = MutableStateFlow<CardSortType>(CardSortType.NEWEST)
    val cardSortTypeState: StateFlow<CardSortType> = _cardSortTypeState

    fun getUserInfo() {
        baseViewModelScope.launch {
            _myPageState.value = UiState.Loading
            commonProfileUseCase()
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _myPageState.value = UiState.Success(it)
                }.flatMap { checkWalletUseCase() }
                .onSuccess { hasWalletState.value = it }
                .flatMap { commonProfileNftUseCase(page = 0, pageSize = PAGE_SIZE, sort = Sort.NEWEST.str) }
                .onSuccess { _userCardState.value = it }
                .onError { e -> catchError(e) }
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
            nftLikeUseCase(nftId)
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

    override fun onUserFollowedClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToUserFollowedClicked(myPageState.value.successOrNull()?.memberInfo?.memberId ?: 0))
        }
    }

    override fun onUserFollowingClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToUserFollowingClicked(myPageState.value.successOrNull()?.memberInfo?.memberId ?: 0))
        }
    }
}
