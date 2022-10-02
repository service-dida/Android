package com.dida.android.presentation.views.nav.mypage

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.android.util.AppLog
import com.dida.android.util.NftActionHandler
import com.dida.data.DataApplication
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.TempPasswordAPI
import com.dida.domain.usecase.main.UpdateProfileAPI
import com.dida.domain.usecase.main.UserNftAPI
import com.dida.domain.usecase.main.UserProfileAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userProfileAPI: UserProfileAPI,
    private val userNftAPI: UserNftAPI,
    private val updateProfileAPI: UpdateProfileAPI,
    private val tempPasswordAPI: TempPasswordAPI
) : BaseViewModel(), MypageActionHandler, NftActionHandler {

    private val TAG = "MyPageViewModel"

    private val _navigationEvent: MutableSharedFlow<MypageNavigationAction> =
        MutableSharedFlow<MypageNavigationAction>()
    val navigationEvent: SharedFlow<MypageNavigationAction> = _navigationEvent

    private val _myPageState: MutableStateFlow<UiState<UserProfile>> =
        MutableStateFlow(UiState.Loading)
    val myPageState: StateFlow<UiState<UserProfile>> = _myPageState

    private val _hasWalletState = MutableStateFlow<Boolean>(false)
    val hasWalletState: StateFlow<Boolean> = _hasWalletState

    private val _hasMyNftState: MutableStateFlow<List<UserNft>> =
        MutableStateFlow<List<UserNft>>(emptyList())
    val hasMyNftState: StateFlow<List<UserNft>> = _hasMyNftState

    fun initMyPageState() {
        getUserProfile()
        getUserCards()
    }

    private fun getUserProfile() {
        baseViewModelScope.launch {
            userProfileAPI()
                .onSuccess { _myPageState.value = UiState.Success(it) }
                .onError { e -> catchError(e) }
        }
    }

    private fun getUserCards() {
        baseViewModelScope.launch {
            userNftAPI()
                .onSuccess { _hasMyNftState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun updateProfile(description: MultipartBody.Part, file: MultipartBody.Part) {
        baseViewModelScope.launch {
            updateProfileAPI(description, file)
                .onSuccess { getUserProfile() }
                .onError { e -> catchError(e) }
        }
    }

    fun tempPassword() {
        baseViewModelScope.launch {
            tempPasswordAPI()
                .onSuccess {
                    catchError(
                        Throwable(
                            message = "임시비밀번호 발급성공",
                            cause = null
                        )
                    )
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

    override fun onSettingClicked() {
        baseViewModelScope.launch {
            DataApplication.mySharedPreferences.removeAccessToken()
            _navigationEvent.emit(MypageNavigationAction.NavigateToHome)
        }
    }

    override fun onNftItemClicked(nftId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToDetailNft(nftId.toLong()))
        }
    }
}