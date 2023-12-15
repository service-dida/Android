package com.dida.swap

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CheckWalletUseCase
import com.dida.domain.usecase.MemberWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwapViewModel @Inject constructor(
    private val memberWalletUseCase: MemberWalletUseCase,
    private val checkWalletUseCase: CheckWalletUseCase
) : BaseViewModel(), SwapActionHandler {

    private val TAG = "SwapViewModel"

    private val _navigationEvent: MutableSharedFlow<SwapNavigationAction> = MutableSharedFlow<SwapNavigationAction>()
    val navigationEvent: SharedFlow<SwapNavigationAction> = _navigationEvent

    private val _walletExistsState: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val walletExistsState: SharedFlow<Boolean> = _walletExistsState

    private val _swapTypeState: MutableStateFlow<SwapType> = MutableStateFlow<SwapType>(SwapType.KLAY_TO_DIDA)
    val swapTypeState: StateFlow<SwapType> = _swapTypeState

    private val _walletAmountState = MutableStateFlow<String>("0.0")
    val walletAmountState: StateFlow<String> = _walletAmountState

    val amountInputState: MutableStateFlow<String> = MutableStateFlow<String>("")

    private var klayAmount: String = ""
    private var didaAmount: String = ""

    fun initWalletAmount() {
        baseViewModelScope.launch {
            memberWalletUseCase()
                .onSuccess {
                    klayAmount = it.klay.toString()
                    didaAmount = it.dida.toString()
                    setWalletAmount()
                }.onError { e -> catchError(e) }
        }
    }

    private fun setWalletAmount() {
        when (swapTypeState.value) {
            SwapType.KLAY_TO_DIDA ->  _walletAmountState.value = klayAmount
            SwapType.DIDA_TO_KLAY -> _walletAmountState.value = didaAmount
        }
    }

    fun getWalletExists() {
        baseViewModelScope.launch {
            showLoading()
            checkWalletUseCase()
                .onSuccess { _walletExistsState.emit(it) }
                .onError { e -> catchError(e) }
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
            when (swapTypeState.value) {
                SwapType.KLAY_TO_DIDA -> _swapTypeState.emit(SwapType.DIDA_TO_KLAY)
                SwapType.DIDA_TO_KLAY -> _swapTypeState.emit(SwapType.KLAY_TO_DIDA)
            }
            setWalletAmount()
        }
    }
}

enum class SwapType {
    KLAY_TO_DIDA,
    DIDA_TO_KLAY
}
