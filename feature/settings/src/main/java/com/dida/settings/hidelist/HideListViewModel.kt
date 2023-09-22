package com.dida.settings.hidelist

import com.dida.common.base.BaseViewModel
import com.dida.common.util.INIT_PAGE
import com.dida.common.util.PAGE_SIZE
import com.dida.common.util.UiState
import com.dida.domain.Contents
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.HideNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CancelBlockUseCase
import com.dida.domain.usecase.HideNftsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HideListViewModel @Inject constructor(
    private val hideNftsUseCase: HideNftsUseCase,
    private val cancelBlockUseCase: CancelBlockUseCase
) : BaseViewModel() , HideListActionHandler {

    private val TAG = "HideListViewModel"

    private val _navigationEvent: MutableSharedFlow<HideListNavigationAction> = MutableSharedFlow<HideListNavigationAction>()
    val navigationEvent: SharedFlow<HideListNavigationAction> = _navigationEvent

    private val _hideCardState: MutableStateFlow<Contents<HideNft>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, content = emptyList())
    )
    val hideCardState: StateFlow<Contents<HideNft>> = _hideCardState

    init {
        getHideList()
    }

    private fun getHideList() {
        baseViewModelScope.launch {
            hideNftsUseCase(INIT_PAGE, PAGE_SIZE)
                .onSuccess { _hideCardState.emit(it) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HideListNavigationAction.NavigateToDetailNft(nftId))
        }
    }

    override fun onHideClicked(nftId: Long) {
        baseViewModelScope.launch {
            showLoading()
            cancelBlockUseCase(type = Block.NFT, blockId = nftId)
                .onSuccess {
                    val index = hideCardState.value.content.toMutableList().indexOfFirst { it.nftId == nftId }
                    val newList = hideCardState.value.content.toMutableList().apply {
                        removeAt(index)
                    }
                    _hideCardState.value = hideCardState.value.copy(content = newList)
                }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun onNextPage() {
        baseViewModelScope.launch {
            hideNftsUseCase(hideCardState.value.page + 1, PAGE_SIZE)
                .onSuccess {
                    it.content = (hideCardState.value.content.toMutableList()) + it.content
                    _hideCardState.emit(it)
                }
                .onError { e -> catchError(e) }
        }
    }
}
