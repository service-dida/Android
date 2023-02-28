package com.dida.mypage

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.domain.flatMap
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
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


    enum class MypageNftType{
        NEWEST,
        OLDEST
    }
    private val _mypageNftTypeState: MutableStateFlow<MypageNftType> = MutableStateFlow<MypageNftType>(MypageNftType.NEWEST)
    val mypageNftTypeState: StateFlow<MypageNftType> = _mypageNftTypeState

    fun getMypage() {
        baseViewModelScope.launch {
            userNftAPI()
                .onSuccess {
                    val list = it.sortedByDescending { it.cardId }
                    _hasMyNftState.value = UiState.Success(list)
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

    override fun onWalletClicked() {
        baseViewModelScope.launch {
            if (hasWalletState.value) {
                _navigationEvent.emit(MypageNavigationAction.NavigateToWallet)
            } else {
                _navigationEvent.emit(MypageNavigationAction.NavigateToEmail)
            }
        }
    }

    override fun onNftItemClicked(nftId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToDetailNft(nftId.toLong()))
        }
    }

    override fun onLikeBtnClicked(nftId: Int) {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(nftId.toLong())
                .onSuccess {
                    getMypage()
                    dismissLoading()}
                .onError { e -> catchError(e) }
        }
    }

    override fun onMypageNftTypeClicked(type: MypageNftType) {
        _mypageNftTypeState.value = type
    }

    override fun onSettingsClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToSettings)
        }
    }
}
