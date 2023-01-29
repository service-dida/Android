package com.dida.swap.loading

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.SwapDidaToKlayAPI
import com.dida.domain.usecase.main.SwapKlayToDidaAPI
import com.dida.domain.usecase.main.WalletAmountAPI
import com.dida.swap.SwapViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwapLoadingViewModel @Inject constructor(
    private val swapKlayToDidaApi: SwapKlayToDidaAPI,
    private val swapDidaToKlayAPI: SwapDidaToKlayAPI
) : BaseViewModel() {

    private val TAG = "SwapLoadingViewModel"

    private val _navigationEvent: MutableSharedFlow<SwapLoadingNavigationAction> =
        MutableSharedFlow<SwapLoadingNavigationAction>()
    val navigationEvent: SharedFlow<SwapLoadingNavigationAction> = _navigationEvent


    fun swap(swapType : SwapViewModel.SwapType, password: String, amount: Double) {
        baseViewModelScope.launch {
            if (swapType == SwapViewModel.SwapType.KLAY_TO_DIDA) {
                swapKlayToDidaApi(password, amount)
                    .onSuccess {
                        _navigationEvent.emit(SwapLoadingNavigationAction.NavigateToSuccess)
                    }
                    .onError { e ->
                        //TODO : 실패 시 로직 구현해야함
                        catchError(e)
                    }
            } else {
                swapDidaToKlayAPI(password, amount)
                    .onSuccess {
                        _navigationEvent.emit(SwapLoadingNavigationAction.NavigateToSuccess)
                    }
                    .onError { e ->
                        //TODO : 실패 시 로직 구현해야함
                        catchError(e)
                    }
            }
        }
    }
}