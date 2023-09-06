package com.dida.add.main

import com.dida.common.base.BaseViewModel
import com.dida.data.model.NeedToWalletException
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.WalletExistedAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val walletExistedAPI: WalletExistedAPI,
) : BaseViewModel() {

    private val TAG = "AddViewModel"

    private val _walletExistsState: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val walletExistsState: SharedFlow<Boolean> = _walletExistsState.asSharedFlow()

    fun getWalletExists() {
        baseViewModelScope.launch {
            showLoading()
            walletExistedAPI()
                .onSuccess { _walletExistsState.emit(it) }
                .onError { e ->
                    when (e) {
                        is NeedToWalletException -> _walletExistsState.emit(false)
                        else -> catchError(e)
                    }
                }
            dismissLoading()
        }
    }
}
