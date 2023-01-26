package com.dida.swap

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.SwapDidaToKlayAPI
import com.dida.domain.usecase.main.SwapKlayToDidaAPI
import com.dida.domain.usecase.main.WalletAmountAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwapViewModel @Inject constructor(
    private val swapKlayToDidaApi: SwapKlayToDidaAPI,
    private val swapDidaToKlayAPI: SwapDidaToKlayAPI,
    private val walletAmountAPI: WalletAmountAPI
) : BaseViewModel() , SwapActionHandler{

    private val TAG = "SwapViewModel"

    enum class SwapType{
        KLAY_TO_DIDA,
        DIDA_TO_KLAY
    }

    private val _navigationEvent: MutableSharedFlow<SwapNavigationAction> = MutableSharedFlow<SwapNavigationAction>()
    val navigationEvent: SharedFlow<SwapNavigationAction> = _navigationEvent

    private val _swapTypeState: MutableStateFlow<SwapType> = MutableStateFlow<SwapType>(SwapType.KLAY_TO_DIDA)
    val swapTypeState: StateFlow<SwapType> = _swapTypeState

    private val _walletAmountState = MutableStateFlow<String>("0.0")
    val walletAmountState: StateFlow<String> = _walletAmountState

    fun getWalletAmount(){
        baseViewModelScope.launch {
            if(swapTypeState.value == SwapType.KLAY_TO_DIDA){
                walletAmountAPI()
                    .onSuccess { _walletAmountState.emit(it.klay.toString()) }
                    .onError { e -> catchError(e) }
            }else{
                walletAmountAPI()
                    .onSuccess { _walletAmountState.emit(it.dida.toString()) }
                    .onError { e -> catchError(e) }
            }
        }
    }

    fun swap(password: String, amount : Double){
        baseViewModelScope.launch {
            if(swapTypeState.value == SwapType.KLAY_TO_DIDA){
                swapKlayToDidaApi(password,amount)
                    .onSuccess {  }
                    .onError { e -> catchError(e) }
            }else{
                swapDidaToKlayAPI(password,amount)
                    .onSuccess {  }
                    .onError { e -> catchError(e) }
            }
        }
    }


    override fun onSwapClicked() {
        baseViewModelScope.launch {
            getWalletAmount()
            _navigationEvent.emit(SwapNavigationAction.NavigateToPassword)
        }
    }

    override fun onSwapTypeChange() {
        baseViewModelScope.launch {
            if(swapTypeState.value == SwapType.KLAY_TO_DIDA){
                _swapTypeState.emit(SwapType.DIDA_TO_KLAY)
            }else{
                _swapTypeState.emit(SwapType.KLAY_TO_DIDA)
            }
        }
    }
}