package com.dida.swap.success

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
class SwapSuccessViewModel @Inject constructor(

) : BaseViewModel() , SwapSuccessActionHandler {

    private val TAG = "SwapSuccessViewModel"

    private val _navigationEvent: MutableSharedFlow<SwapSuccessNavigationAction> =
        MutableSharedFlow<SwapSuccessNavigationAction>()
    val navigationEvent: SharedFlow<SwapSuccessNavigationAction> = _navigationEvent

    override fun onSwapConfirm() {
        //TODO 스왑 내역화면으로 가야함
    }
}