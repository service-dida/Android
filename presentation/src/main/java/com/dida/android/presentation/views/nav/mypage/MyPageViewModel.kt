package com.dida.android.presentation.views.nav.mypage

import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.views.nav.home.HomeActionHandler
import com.dida.android.util.AppLog
import com.dida.android.util.SingleLiveEvent
import com.dida.data.DataApplication
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl,
    private val homeActionHandler: HomeActionHandler
) : BaseViewModel(), MypageActionHandler {

    private val TAG = "MyPageViewModel"

    private val _myPageState: MutableStateFlow<UiState<MypageUiModel>> = MutableStateFlow(UiState.Loading)
    val myPageState: StateFlow<UiState<MypageUiModel>> = _myPageState

    private val _navigationEvent: MutableSharedFlow<MypageNavigationAction> = MutableSharedFlow<MypageNavigationAction>()
    val navigationEvent: SharedFlow<MypageNavigationAction> = _navigationEvent

    private val _hasWalletState = MutableStateFlow<Boolean>(false)
    val hasWalletState: StateFlow<Boolean> = _hasWalletState

    private val _hasMyNftState: MutableStateFlow<List<UserCardsResponseModel>> = MutableStateFlow<List<UserCardsResponseModel>>(emptyList())
    val hasMyNftState: StateFlow<List<UserCardsResponseModel>> = _hasMyNftState

    fun initMyPageState() {
        getUserProfile()
        getUserCards()
    }

    private fun getUserProfile(){
        baseViewModelScope.launch {
            mainRepositoryImpl.getUserProfileAPI()
                .onSuccess {
                    _myPageState.value = UiState.Success(
                        MypageUiModel(
                            cardCnt = it.cardCnt,
                            description = it.description,
                            followerCnt = it.followerCnt,
                            followingCnt = it.followingCnt,
                            getWallet = it.getWallet,
                            nickname = it.nickname,
                            profileUrl = it.profileUrl
                        )
                    )
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    private fun getUserCards(){
        baseViewModelScope.launch {
            mainRepositoryImpl.getUserCardsAPI()
                .onSuccess {
                    AppLog.d(it.toString())
                    _hasMyNftState.value = it
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    override fun onWalletClicked() {
        baseViewModelScope.launch {
            if(hasWalletState.value) {
                _navigationEvent.emit(MypageNavigationAction.NavigateToWallet)
            } else {
                _navigationEvent.emit(MypageNavigationAction.NavigateToEmail)
            }
        }
    }

    override fun onSettingClicked() {
        baseViewModelScope.launch {
            DataApplication.mySharedPreferences.removeAccessToken()
            _navigationEvent.emit(MypageNavigationAction.NavigateToHome)
        }
    }

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToDetailNft)
        }
    }
}

data class MypageUiModel(
    val cardCnt: Int = 0,
    val description: String = "",
    val followerCnt: Int = 0,
    val followingCnt: Int = 0,
    val getWallet: Boolean = false,
    val nickname: String = "",
    val profileUrl: String = ""
)