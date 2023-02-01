package com.dida.swap.history

import com.dida.common.base.BaseViewModel
import com.dida.domain.model.nav.swap_history.SwapHistory
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.SwapHistoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwapHistoryViewModel @Inject constructor(
    private val swapHistoryAPI: SwapHistoryAPI
) : BaseViewModel() , SwapHistoryActionHandler{

    private val TAG = "SwapHistoryViewModel"

    private val _swapHistoryState: MutableStateFlow<List<SwapHistory>> = MutableStateFlow<List<SwapHistory>>(emptyList())
    val swapHistoryState: StateFlow<List<SwapHistory>> = _swapHistoryState.asStateFlow()

    fun getSwapHistory(){
        baseViewModelScope.launch {
            swapHistoryAPI()
                .onSuccess { _swapHistoryState.emit(it)
                it.get(0).timeToDayAndTime()}
                .onError { e -> catchError(e) }
        }
    }
}