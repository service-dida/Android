package com.dida.android.presentation.views.nickname

import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import com.dida.data.DataApplication
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.State
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MainUsecase
import com.kakao.sdk.common.KakaoSdk.type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
) : BaseViewModel(), NicknameActionHandler {

    private val TAG = "NicknameViewModel"

    val emailStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")
    val userInputStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            userInputStateFlow.debounce(500).collect {
                if(it.isEmpty()) {
                    setNicknameVerify(0)
                }
                else if(it.length > 8) {
                    setNicknameVerify(1)
                }
                else {
                    nicknameAPIServer(it)
                }
            }
        }
    }

    private val _navigationEvent: MutableSharedFlow<NicknameNavigationAction> = MutableSharedFlow<NicknameNavigationAction>()
    val navigationEvent: SharedFlow<NicknameNavigationAction> = _navigationEvent

    /**
    0 -> 초기값
    1 -> 8글자 초과일 경우
    2 -> 닉네임이 중복될 경우
    3 -> 닉네임을 사용할 수 있을 경우
    **/
    private val _nickNameCheckStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nickNameCheckStateFlow: StateFlow<String> = _nickNameCheckStateFlow

    private fun setNicknameVerify(type: Int){
        when(type) {
            1 -> _nickNameCheckStateFlow.value = "닉네임은 8글자 이하입니다."
            2 -> _nickNameCheckStateFlow.value = "중복된 닉네임 입니다."
            3 -> _nickNameCheckStateFlow.value = "사용 가능한 닉네임 입니다."
            else -> _nickNameCheckStateFlow.value = ""
        }
    }

    /**
    true -> 이미 사용중인 닉네임
    false -> 사용가능한 닉네임
    **/
    private val _nickNameCheckEvent: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val nickNameCheckEvent: StateFlow<Boolean> = _nickNameCheckEvent

    private fun nicknameAPIServer(nickName: String) {
        baseViewModelScope.launch {
            mainUsecase.nicknameAPI(nickName)
                .onSuccess {
                    _nickNameCheckEvent.value = it.used
                    if(it.used) {
                        setNicknameVerify(2)
                    }
                    else {
                        setNicknameVerify(3)
                    }
                }.onError { e ->
                    setNicknameVerify(0)
                    _nickNameCheckEvent.value = true
                    catchError(e)
                }
        }
    }

    private fun createUserAPIServer(email: String, nickName: String) {
        baseViewModelScope.launch {
            mainUsecase.createUserAPI(email, nickName)
                .onSuccess {
                    DataApplication.mySharedPreferences.setAccessToken(it.accessToken, it.refreshToken)
                    _navigationEvent.emit(NicknameNavigationAction.NavigateToHome)
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    override fun onCreateItemClicked() {
        if(!nickNameCheckEvent.value){
            createUserAPIServer(emailStateFlow.value, userInputStateFlow.value)
        }
    }
}