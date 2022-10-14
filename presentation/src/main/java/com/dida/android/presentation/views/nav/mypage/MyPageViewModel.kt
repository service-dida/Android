package com.dida.android.presentation.views.nav.mypage

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.util.AppLog
import com.dida.android.util.NftActionHandler
import com.dida.android.util.UiState
import com.dida.data.DataApplication
import com.dida.domain.flatMap
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
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
    private val tempPasswordAPI: TempPasswordAPI,
    private val changePasswordAPI: ChangePasswordAPI
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

    private val _hasMyNftState: MutableStateFlow<UiState<List<UserNft>>> =
        MutableStateFlow(UiState.Loading)
    val hasMyNftState: StateFlow<UiState<List<UserNft>>> = _hasMyNftState

    fun getMypage() {
        baseViewModelScope.launch {
            userNftAPI()
                .onSuccess {
                    AppLog.d(it.toString())
                    _hasMyNftState.value = UiState.Success(it)
                }
                .flatMap {
                    userProfileAPI()
                        .onSuccess {
                            _myPageState.value = UiState.Success(it)
                            _hasWalletState.value = it.getWallet }
                        .onError { e -> catchError(e) }
                }
                .onError { e -> catchError(e) }

        }
    }

    fun updateProfile(description: MultipartBody.Part, file: MultipartBody.Part) {
        baseViewModelScope.launch {
            updateProfileAPI(description, file)
                .onSuccess { getMypage() }
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

    fun changePassword(beforePassword: String, afterPassword: String) {
        baseViewModelScope.launch {
            changePasswordAPI(beforePassword, afterPassword)
                .onSuccess {
                    catchError(
                        Throwable(
                            message = "비밀번호 변경 성공",
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

    override fun onLogoutClicked() {
        baseViewModelScope.launch {
            DataApplication.dataStorePreferences.removeAccessToken()
            _navigationEvent.emit(MypageNavigationAction.NavigateToHome)
        }
    }

    override fun onNftItemClicked(nftId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(MypageNavigationAction.NavigateToDetailNft(nftId.toLong()))
        }
    }

    override fun onLikeBtnClicked(nftId: Int) {
        baseViewModelScope.launch {

        }
    }
}