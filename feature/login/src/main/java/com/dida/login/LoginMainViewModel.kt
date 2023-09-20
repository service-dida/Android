package com.dida.login

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val TAG = "LoginMainViewModel"

    private val _navigationEvent: MutableSharedFlow<LoginNavigationAction> = MutableSharedFlow<LoginNavigationAction>()
    val navigationEvent: SharedFlow<LoginNavigationAction> = _navigationEvent

    /** 카카오 로그인 결과
     * refreshToken ->
     * null : 닉네임 입력(처음 회원가입한 유저로 닉네임 입력)
     * notNull : 홈화면 이동(이미 추가회원가입 완료 유저)
     *
     * accessToken ->
     * 추가 회원가입 진행시(email) : email 정보
     * 추가 회원가입 이미 진행 : Token
     **/

    fun loginAPIServer(idToken: String) {
        baseViewModelScope.launch {
            showLoading()
            loginUseCase(idToken)
                .onSuccess {
                    if (it.message.isNullOrEmpty()) {
                        dataStorePreferences.setAccessToken(it.accessToken, it.refreshToken)
                        _navigationEvent.emit(LoginNavigationAction.NavigateToHome)
                    } else {
                        _navigationEvent.emit(LoginNavigationAction.NavigateToNickname(it.message ?: ""))
                    }
                    dismissLoading()
                }.onError { e -> catchError(e) }
        }
    }
}
