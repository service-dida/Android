package com.dida.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CheckNicknameUseCase
import com.dida.domain.usecase.PostUserUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class NicknameViewModel @AssistedInject constructor(
    @Assisted("email") val email: String,
    private val postUserUseCase: PostUserUseCase,
    private val checkNicknameUseCase: CheckNicknameUseCase
) : BaseViewModel(), NicknameActionHandler {

    private val TAG = "NicknameViewModel"

    private val _navigationEvent: MutableSharedFlow<NicknameNavigationAction> = MutableSharedFlow<NicknameNavigationAction>()
    val navigationEvent: SharedFlow<NicknameNavigationAction> = _navigationEvent

    val userInputState: MutableStateFlow<String> = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            userInputState.debounce(500).collect {
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
            checkNicknameUseCase(nickName)
                .onSuccess {
                    _nickNameCheckState.value = it
                    if(it) { setNicknameVerify(2) }
                    else { setNicknameVerify(3) }
                }.onError { e ->
                    setNicknameVerify(0)
                    _nickNameCheckState.value = true
                    catchError(e)
                }
        }
    }

    private fun createUserAPIServer(email: String, nickName: String) {
        baseViewModelScope.launch {
            postUserUseCase(email, nickName)
                .onSuccess {
                    DataApplication.dataStorePreferences.setAccessToken(it.accessToken, it.refreshToken)
                    _navigationEvent.emit(NicknameNavigationAction.NavigateToHome) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onCreateItemClicked() {
        if(!nickNameCheckState.value){
            createUserAPIServer(email, userInputState.value)
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("email") email: String
        ): NicknameViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            email: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(email) as T
            }
        }
    }
}
