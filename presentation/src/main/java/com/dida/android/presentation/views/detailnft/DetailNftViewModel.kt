package com.dida.android.presentation.views.detailnft

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.views.nav.community.CommunityActionHandler
import com.dida.android.util.UiState
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.detailnft.Community
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel(), DetailNftActionHandler, CommunityActionHandler {

    private val TAG = "DetailNftViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailNftNavigationAction> = MutableSharedFlow<DetailNftNavigationAction>()
    val navigationEvent: SharedFlow<DetailNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<UiState<Community>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<Community>> = _detailNftState

    fun getDetailNft() {
        // test용
        // api나오면 사용
//        viewModelScope.launch {
//            mainUsecase.getMainAPI()
//                .onSuccess {
//                    it.catch { e ->
//                        catchError(e)
//                    }
//                    it.collect { data ->
//                        _homeStateFlow.value = UiState.Success(data)
//                        onSoldOutDayClicked(_termStateFlow.value)
//                    }
//                }.onError { e ->
//                    catchError(e)
//                }
//        }
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