package com.dida.android.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import com.dida.data.DataApplication.Companion.mySharedPreferences
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(private val mainRepositoryImpl: MainRepositoryImpl) :
    BaseViewModel() {

    private val TAG = "LoginMainViewModel"

    private val _navigationEvent: MutableSharedFlow<LoginNavigationAction> = MutableSharedFlow<LoginNavigationAction>()
    val navigationEvent: SharedFlow<LoginNavigationAction> = _navigationEvent

    /** 카카오 로그인 결과
     * refreshToken ->
     * null : 닉네임 입력(처음 회원가입한 유저로 닉네임 입력)
     * notNull : 홈화면 이동(이미 추가회원가입 완료 유저)
     **/

    fun loginAPIServer(idToken: String) {
        baseViewModelScope.launch {
            mainRepositoryImpl.loginAPI(idToken)
                .onSuccess {
                    if(it.refreshToken.isNullOrEmpty()) {
                        _navigationEvent.emit(LoginNavigationAction.NavigateToNickname(it.accessToken!!))
                    }
                    else {
                        mySharedPreferences.setAccessToken(it.accessToken, it.refreshToken)
                        _navigationEvent.emit(LoginNavigationAction.NavigateToHome)
                    }
                }.onError { e ->
                    catchError(e)
                    _navigationEvent.emit(LoginNavigationAction.NavigateToLoginFail)
                    mySharedPreferences.removeAccessToken()
                }
        }
    }
}