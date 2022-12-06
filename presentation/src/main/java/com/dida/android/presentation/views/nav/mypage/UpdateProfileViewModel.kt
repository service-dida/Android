package com.dida.android.presentation.views.nav.mypage

import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.NicknameCheckAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val nicknameCheckAPI: NicknameCheckAPI
) : BaseViewModel() {

    private val TAG = "UpdateProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<MypageNavigationAction> =
        MutableSharedFlow<MypageNavigationAction>()
    val navigationEvent: SharedFlow<MypageNavigationAction> = _navigationEvent

    val nickNameState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val availableConfirm : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    init {
        baseViewModelScope.launch {
            nickNameState.debounce(500).collect {
                if(it.isEmpty()) { setNicknameVerify(0) }
                else if(it.length > 8) { setNicknameVerify(1) }
                else { nicknameAPIServer(it) }
            }
        }

    }
    /**
    0 -> 초기값
    1 -> 8글자 초과일 경우
    2 -> 닉네임이 중복될 경우
    3 -> 닉네임을 사용할 수 있을 경우
     **/
    private val _nickNameCheckTextState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nickNameCheckTextState: StateFlow<String> = _nickNameCheckTextState

    private fun setNicknameVerify(type: Int){
        when(type) {
            1 -> _nickNameCheckTextState.value = "닉네임은 8글자 이하입니다."
            2 -> _nickNameCheckTextState.value = "중복된 닉네임 입니다."
            3 -> _nickNameCheckTextState.value = "사용 가능한 닉네임 입니다."
            else -> _nickNameCheckTextState.value = ""
        }
    }

    /**
    true -> 이미 사용중인 닉네임
    false -> 사용가능한 닉네임
     **/
    private val _nickNameCheckState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val nickNameCheckState: StateFlow<Boolean> = _nickNameCheckState

    private fun nicknameAPIServer(nickName: String) {
        baseViewModelScope.launch {
            nicknameCheckAPI(nickName)
                .onSuccess {
                    _nickNameCheckState.value = it.used
                    if(it.used) { setNicknameVerify(2) }
                    else { setNicknameVerify(3) }
                }.onError { e ->
                    setNicknameVerify(0)
                    _nickNameCheckState.value = true
                    catchError(e)
                }
        }
    }
}