package com.dida.settings.hidelist

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.common.util.UiState
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

// TODO : 페이징 적용 필요
@HiltViewModel
class HideListViewModel @Inject constructor(
    private val hideNftsUseCase: HideNftsUseCase,
    private val cancelBlockUseCase: CancelBlockUseCase
) : BaseViewModel() , HideListActionHandler {

    private val TAG = "HideListViewModel"

    private val _navigationEvent: MutableSharedFlow<HideListNavigationAction> = MutableSharedFlow<HideListNavigationAction>()
    val navigationEvent: SharedFlow<HideListNavigationAction> = _navigationEvent

    private val _HideCardState: MutableStateFlow<UiState<List<HideNft>>> = MutableStateFlow(UiState.Loading)
    val hideCardState: StateFlow<UiState<List<HideNft>>> = _HideCardState

    fun getHideList() {
        baseViewModelScope.launch {
            hideNftsUseCase(0, PAGE_SIZE)
                .onSuccess { _HideCardState.emit(UiState.Success(it.content)) }
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
            cancelBlockUseCase(type = Block.NFT, blockId = nftId)
                .onSuccess {  }
                .onError { e -> catchError(e) }
        }
    }
}
