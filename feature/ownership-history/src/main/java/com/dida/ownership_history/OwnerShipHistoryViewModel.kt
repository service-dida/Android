package com.dida.ownership_history

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.OwnershipHistory
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.GetOwnershipHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerShipHistoryViewModel @Inject constructor(
    private val ownershipHistoryUseCase: GetOwnershipHistoryUseCase
) : BaseViewModel() {

    private val TAG = "OwnerShipHistoryViewModel"

    private val _ownershipHistoryState: MutableStateFlow<Contents<OwnershipHistory>> = MutableStateFlow<Contents<OwnershipHistory>>(
        Contents(page = 0, pageSize = 0, content = emptyList())
    )
    val ownershipHistoryState: StateFlow<Contents<OwnershipHistory>> = _ownershipHistoryState.asStateFlow()

    private val nftIdState: MutableStateFlow<Long> = MutableStateFlow(0)
    fun setNftId(nftId: Long) {
        nftIdState.value = nftId
    }

    fun getOwnershipHistory(){
        baseViewModelScope.launch {
            ownershipHistoryUseCase(nftId = nftIdState.value, page = 0, pageSize = PAGE_SIZE)
                .onSuccess {
                    _ownershipHistoryState.emit(it)
                }.onError { e -> catchError(e) }
        }
    }

    fun nextPage() {
        baseViewModelScope.launch {
            if (!ownershipHistoryState.value.hasNext) return@launch
            ownershipHistoryUseCase(nftId = nftIdState.value, page = ownershipHistoryState.value.page + 1, pageSize = PAGE_SIZE)
                .onSuccess {
                    it.content = (ownershipHistoryState.value.content.toMutableList() + it.content)
                    _ownershipHistoryState.value = it
                }.onError { e -> catchError(e) }
        }
    }
}
