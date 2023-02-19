package com.dida.change_password

import com.dida.common.base.BaseViewModel
import com.dida.common.util.AppLog
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.flatMap
import com.dida.domain.model.nav.mypage.OtherUserProfie
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordAPI: ChangePasswordAPI
) : BaseViewModel(), ChangePasswordActionHandler {

    private val TAG = "ChangePasswordViewModel"

    private val _navigationEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigationEvent: SharedFlow<Unit> = _navigationEvent.asSharedFlow()

    val beforePasswordState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val newPasswordState: MutableStateFlow<String> = MutableStateFlow<String>("")


    override fun onOkBtnClicked() {
        baseViewModelScope.launch {
            showLoading()
            changePasswordAPI(beforePassword = beforePasswordState.value, afterPassword = newPasswordState.value)
                .onSuccess { _navigationEvent.emit(Unit) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

}
