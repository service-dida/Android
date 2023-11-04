package com.dida.swap.history

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.Swap
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MemberSwapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwapHistoryViewModel @Inject constructor(
    private val memberSwapUseCase: MemberSwapUseCase,
) : BaseViewModel() , SwapHistoryActionHandler{

    private val TAG = "SwapHistoryViewModel"


    private val _navigationEvent: MutableSharedFlow<SwapHistoryNavigationAction> = MutableSharedFlow<SwapHistoryNavigationAction>()
    val navigationEvent: SharedFlow<SwapHistoryNavigationAction> = _navigationEvent

    private val _swapHistoryState: MutableStateFlow<Contents<Swap>> = MutableStateFlow<Contents<Swap>>(
        Contents(page = 0, pageSize = 0, content = emptyList())
    )
    val swapHistoryState: StateFlow<Contents<Swap>> = _swapHistoryState.asStateFlow()

    fun getSwapHistory(){
        baseViewModelScope.launch {
            memberSwapUseCase(page = 0, size = PAGE_SIZE)
                .onSuccess {
                    _swapHistoryState.emit(it)
                    _navigationEvent.emit(SwapHistoryNavigationAction.finishGetSwapHistory)
                }.onError { e -> catchError(e) }
        }
    }

    fun nextPage() {
        baseViewModelScope.launch {
            if (!swapHistoryState.value.hasNext) return@launch
            memberSwapUseCase(page = swapHistoryState.value.page + 1, size = PAGE_SIZE)
                .onSuccess {
                    it.content = (swapHistoryState.value.content.toMutableList() + it.content)
                    _swapHistoryState.value = it
                    _navigationEvent.emit(SwapHistoryNavigationAction.finishGetSwapHistory)
                }.onError { e -> catchError(e) }
        }
    }
}
