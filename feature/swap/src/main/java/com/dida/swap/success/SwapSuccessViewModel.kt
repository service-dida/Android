package com.dida.swap.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.swap.SwapType
import com.dida.swap.SwapViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SwapSuccessViewModel @AssistedInject constructor(
    @Assisted("swapType") val swapType: SwapType
) : BaseViewModel() , SwapSuccessActionHandler {

    private val TAG = "SwapSuccessViewModel"

    private val _navigationEvent: MutableSharedFlow<SwapSuccessNavigationAction> = MutableSharedFlow<SwapSuccessNavigationAction>()
    val navigationEvent: SharedFlow<SwapSuccessNavigationAction> = _navigationEvent

    override fun onSwapConfirm() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SwapSuccessNavigationAction.NavigateToHistory)
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("swapType") swapType: SwapType
        ): SwapSuccessViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            swapType: SwapType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(swapType) as T
            }
        }
    }
}
