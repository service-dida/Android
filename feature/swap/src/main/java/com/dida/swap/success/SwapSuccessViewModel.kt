package com.dida.swap.success

import com.dida.common.base.BaseViewModel
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

    private val _swapTypeState: MutableStateFlow<SwapViewModel.SwapType> =
        MutableStateFlow<SwapViewModel.SwapType>(SwapViewModel.SwapType.KLAY_TO_DIDA)
    val swapTypeState: StateFlow<SwapViewModel.SwapType> = _swapTypeState

    fun initSuccessData(swapType : SwapViewModel.SwapType){
        baseViewModelScope.launch {
            _swapTypeState.emit(swapType)
        }
    }
    override fun onSwapConfirm() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SwapSuccessNavigationAction.NavigateToHistory)
        }
    }
}