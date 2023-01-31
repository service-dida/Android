package com.dida.swap.history

import com.dida.common.base.BaseViewModel
import com.dida.common.util.UiState
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
) : BaseViewModel() {

    private val TAG = "SwapHistoryViewModel"

    private val _swapHistoryState: MutableStateFlow<UiState<List<SwapHistory>>> = MutableStateFlow(UiState.Loading)
    val swapHistoryState: StateFlow<UiState<List<SwapHistory>>> = _swapHistoryState.asStateFlow()

    fun getSwapHistory(){
        baseViewModelScope.launch {
            swapHistoryAPI()
                .onSuccess { _swapHistoryState.value = UiState.Success(it) }
                .onError { e -> catchError(e) }
        }
    }
}