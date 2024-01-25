package com.dida.update.profile

import android.net.Uri
import com.dida.common.base.BaseViewModel
import com.dida.domain.NetworkResult
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CheckNicknameUseCase
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.PatchProfileDescriptionUseCase
import com.dida.domain.usecase.PatchProfileImageUseCase
import com.dida.domain.usecase.PatchProfileNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val checkNicknameUseCase: CheckNicknameUseCase,
    private val patchProfileImageUseCase: PatchProfileImageUseCase,
    private val patchProfileNicknameUseCase: PatchProfileNicknameUseCase,
    private val patchProfileDescriptionUseCase: PatchProfileDescriptionUseCase,
    private val commonProfileUseCase: CommonProfileUseCase
) : BaseViewModel(), UpdateProfileActionHandler {

    private val TAG = "UpdateProfileViewModel"

    private val _navigationEvent: MutableSharedFlow<UpdateProfileNavigationAction> = MutableSharedFlow()
    val navigationEvent: SharedFlow<UpdateProfileNavigationAction> = _navigationEvent

    private val _updateCheckState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val updateCheckState: StateFlow<Boolean> = _updateCheckState

    //현재 프로필 정보를 저장
    val profileImageState : MutableStateFlow<String> = MutableStateFlow("")
    val nickNameState: MutableStateFlow<String> = MutableStateFlow("")
    val descriptionState: MutableStateFlow<String> = MutableStateFlow("")

    //초기 프로필 정보를 저장 , 초기와 같은 정보들은 수정 API를 호출안하기 위함
    private lateinit var currentNickname : String
    private lateinit var currentProfileImage : String
    private lateinit var currentDescription : String

    init {
        baseViewModelScope.launch {
            commonProfileUseCase.invoke()
                .onSuccess {
                    profileImageState.emit(it.memberInfo.profileImgUrl ?: "")
                    nickNameState.emit(it.memberInfo.memberName)
                    descriptionState.emit(it.description ?: "")

                    currentProfileImage = it.memberInfo.profileImgUrl ?: ""
                    currentNickname = it.memberInfo.memberName
                    currentDescription = it.description ?: ""
                }.onError { e -> catchError(e) }
        }
    }

    /**
    true -> 이미 사용중인 닉네임
    false -> 사용가능한 닉네임
     **/
    private val _nickNameCheckState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val nickNameCheckState: StateFlow<Boolean> = _nickNameCheckState

    private val _descriptionCheckState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val descriptionCheckState: StateFlow<Boolean> = _descriptionCheckState

    init {
        baseViewModelScope.launch {
            launch {
                nickNameState.collect{
                    _updateCheckState.value = false
                }
            }
            launch {
                nickNameState.debounce(500).collect {
                    if (it == currentNickname) {
                        setNicknameVerify(4)
                        _nickNameCheckState.value = false
                    } else {
                        if (it.isEmpty()) setNicknameVerify(0)
                        else if (it.length > 8) setNicknameVerify(1)
                        else nicknameAPIServer(it)
                    }
                }
            }

            launch {
                descriptionState.collect {
                    _descriptionCheckState.value = it.length <= 30
                    updateCheck()
                }
            }
        }
    }

    private fun nicknameAPIServer(nickName: String) {
        baseViewModelScope.launch {
            checkNicknameUseCase(nickName)
                .onSuccess {
                    _nickNameCheckState.value = it
                    if (it) setNicknameVerify(2)
                    else setNicknameVerify(3)
                }.onError { e ->
                    setNicknameVerify(0)
                    _nickNameCheckState.value = true
                    catchError(e)
                }
        }
    }

    private val _nickNameCheckTextState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nickNameCheckTextState: StateFlow<String> = _nickNameCheckTextState

    private fun setNicknameVerify(type: Int){
        when(type) {
            1 -> _nickNameCheckTextState.value = "닉네임은 8글자 이하입니다."
            2 -> _nickNameCheckTextState.value = "중복된 닉네임 입니다."
            3 -> _nickNameCheckTextState.value = "사용 가능한 닉네임 입니다."
            4 -> _nickNameCheckTextState.value = "현재 닉네임과 동일합니다."
            else -> {
                _nickNameCheckTextState.value = ""
                _nickNameCheckState.value = true
            }
        }
        updateCheck()
    }

    private fun updateCheck(){
        _updateCheckState.value = descriptionCheckState.value && !nickNameCheckState.value
    }

    fun updateProfile(){
        baseViewModelScope.launch {
            showLoading()
            val deferreds : MutableList<Deferred<NetworkResult<Unit>>> = mutableListOf()
            if (currentProfileImage != profileImageState.value) {
                deferreds.add(async {
                    patchProfileImageUseCase(profileImageMultipartState.value!!)
                        .onError { e -> catchError(e) }
                })
            }
            if (currentNickname != nickNameState.value) {
                deferreds.add(async {
                    patchProfileNicknameUseCase(nickname = nickNameState.value)
                        .onError { e -> catchError(e) }
                })
            }
            if (currentDescription != descriptionState.value) {
                deferreds.add(async {
                    patchProfileDescriptionUseCase(description = descriptionState.value)
                        .onError { e -> catchError(e) }
                })
            }
            deferreds.awaitAll()
            dismissLoading()
            _navigationEvent.emit(UpdateProfileNavigationAction.NavigateToBack)
        }
    }

    val profileImageMultipartState : MutableStateFlow<MultipartBody.Part?> = MutableStateFlow<MultipartBody.Part?>(null)

    fun selectProfileImage(url : Uri, multipartBody: MultipartBody.Part){
        profileImageMultipartState.value = multipartBody
        profileImageState.value = url.toString()
    }

    override fun onProfileImageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(UpdateProfileNavigationAction.NavigateToUpdateProfileImage)
        }
    }
}
