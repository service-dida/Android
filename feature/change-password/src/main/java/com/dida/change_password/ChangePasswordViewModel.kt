package com.dida.change_password

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.ChangePasswordAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
