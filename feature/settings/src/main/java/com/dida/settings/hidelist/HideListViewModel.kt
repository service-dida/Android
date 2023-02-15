package com.dida.settings.hidelist

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.UiState
import com.dida.data.DataApplication
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.hide.CardHideList
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CreateUserAPI
import com.dida.domain.usecase.main.HideCancelNftAPI
import com.dida.domain.usecase.main.HideListAPI
import com.dida.domain.usecase.main.NicknameCheckAPI
import com.dida.settings.SettingsNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val _cardHideListState: MutableStateFlow<UiState<List<CardHideList>>> = MutableStateFlow(UiState.Loading)
    val cardHideListState: StateFlow<UiState<List<CardHideList>>> = _cardHideListState

    fun getHideList(){
        baseViewModelScope.launch {
            hideListAPI()
                .onSuccess { _cardHideListState.emit(UiState.Success(it)) }
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
