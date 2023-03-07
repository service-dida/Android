package com.dida.temp_password

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.usecase.main.TempPasswordAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TempPasswordViewModel @Inject constructor(
    private val tempPasswordAPI: TempPasswordAPI
) : BaseViewModel(), TempPasswordActionHandler {

    private val TAG = "TempPasswordViewModel"

    private val _navigationEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigationEvent: SharedFlow<Unit> = _navigationEvent.asSharedFlow()

    init {
        baseViewModelScope.launch {
            showLoading()
            tempPasswordAPI()
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onPasswordChangeClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(Unit)
        }
    }
}
