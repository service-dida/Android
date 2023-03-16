package com.dida.swap

import com.dida.common.base.BaseViewModel
import com.dida.data.model.NeedToWalletException
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.WalletAmountAPI
import com.dida.domain.usecase.main.WalletExistedAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwapViewModel @Inject constructor(
    private val walletAmountAPI: WalletAmountAPI,
    private val walletExistedAPI: WalletExistedAPI
) : BaseViewModel(), SwapActionHandler {

    private val TAG = "SwapViewModel"

    enum class SwapType {
        KLAY_TO_DIDA,
        DIDA_TO_KLAY
    }

    private val _navigationEvent: MutableSharedFlow<SwapNavigationAction> = MutableSharedFlow<SwapNavigationAction>()
    val navigationEvent: SharedFlow<SwapNavigationAction> = _navigationEvent

    private val _walletExistsState: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val walletExistsState: SharedFlow<Boolean> = _walletExistsState

    private val _swapTypeState: MutableStateFlow<SwapType> = MutableStateFlow<SwapType>(SwapType.KLAY_TO_DIDA)
    val swapTypeState: StateFlow<SwapType> = _swapTypeState

    private val _walletAmountState = MutableStateFlow<String>("0.0")
    val walletAmountState: StateFlow<String> = _walletAmountState

    val amountInputState: MutableStateFlow<String> = MutableStateFlow<String>("")

    lateinit var klayAmount: String
    lateinit var didaAmount: String

    fun initWalletAmount() {
        baseViewModelScope.launch {
            walletAmountAPI()
                .onSuccess {
                    klayAmount = it.klay.toString()
                    didaAmount = it.dida.toString()
                    setWalletAmount()
                }
                .onError { e -> catchError(e) }
        }
    }

    fun setWalletAmount() {
        if (swapTypeState.value == SwapType.KLAY_TO_DIDA) {
            _walletAmountState.value = klayAmount
        } else {
            _walletAmountState.value = didaAmount
        }
    }

    fun getWalletExists() {
        baseViewModelScope.launch {
            showLoading()
            walletExistedAPI()
                .onSuccess {
                    _walletExistsState.emit(it) }
                .onError { e ->
                    if (e is NeedToWalletException) _walletExistsState.emit(false)
                    else catchError(e) }
            dismissLoading()
        }
    }
    override fun onSwapClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SwapNavigationAction.NavigateToPassword)
        }
    }

    override fun onSwapTypeChange() {
        baseViewModelScope.launch {
            if (swapTypeState.value == SwapType.KLAY_TO_DIDA) {
                _swapTypeState.emit(SwapType.DIDA_TO_KLAY)
            } else {
                _swapTypeState.emit(SwapType.KLAY_TO_DIDA)
            }
            setWalletAmount()
        }
    }
}
