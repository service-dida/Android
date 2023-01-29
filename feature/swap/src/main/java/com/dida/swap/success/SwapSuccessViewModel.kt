package com.dida.swap.success

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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
        baseViewModelScope.launch {
            _navigationEvent.emit(SwapSuccessNavigationAction.NavigateToHistory)
        }
    }
}