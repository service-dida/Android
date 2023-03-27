package com.dida.settings.hidelist

import com.dida.common.base.BaseViewModel
import com.dida.common.util.UiState
import com.dida.domain.model.main.HideCard
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.HideCancelNftAPI
import com.dida.domain.usecase.main.HideListAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HideListViewModel @Inject constructor(
    private val hideListAPI: HideListAPI,
    private val hideCancelNftAPI: HideCancelNftAPI
) : BaseViewModel() , HideListActionHandler {

    private val TAG = "HideListViewModel"

    private val _navigationEvent: MutableSharedFlow<HideListNavigationAction> = MutableSharedFlow<HideListNavigationAction>()
    val navigationEvent: SharedFlow<HideListNavigationAction> = _navigationEvent

    private val _HideCardState: MutableStateFlow<UiState<List<HideCard>>> = MutableStateFlow(UiState.Loading)
    val hideCardState: StateFlow<UiState<List<HideCard>>> = _HideCardState

    fun getHideList(){
        baseViewModelScope.launch {
            hideListAPI()
                .onSuccess { _HideCardState.emit(UiState.Success(it)) }
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
            hideCancelNftAPI(nftId)
                .onSuccess { getHideList() }
                .onError { e -> catchError(e) }
        }
    }
}
