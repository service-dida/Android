package com.dida.android.presentation.views.detailnft

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.views.nav.community.CommunityActionHandler
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.detailnft.Community
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.DetailNftAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val detailNftAPI: DetailNftAPI
) : BaseViewModel(), DetailNftActionHandler, CommunityActionHandler {

    private val TAG = "DetailNftViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailNftNavigationAction> = MutableSharedFlow<DetailNftNavigationAction>()
    val navigationEvent: SharedFlow<DetailNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<UiState<DetailNFT>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<DetailNFT>> = _detailNftState

    fun getDetailNft(cardId : Long) {
        baseViewModelScope.launch {
            detailNftAPI(cardId)
                .onSuccess {
                    _detailNftState.value = UiState.Success(it)
                    dismissLoading()
                }
                .onError {
                    e -> catchError(e)
                }
        }

    }

    override fun onCommunityMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToCommunity)
        }
    }

    override fun onCommunityItemClicked(communityId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToItemCommunity(communityId))
        }
    }
}