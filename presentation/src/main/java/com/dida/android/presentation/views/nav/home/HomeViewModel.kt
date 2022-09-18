package com.dida.android.presentation.views.nav.home

import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationEvent: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationEvent: SharedFlow<HomeNavigationAction> = _navigationEvent

    private val _homeStateFlow: MutableStateFlow<UiState<Home>> = MutableStateFlow(UiState.Loading)
    val homeStateFlow: StateFlow<UiState<Home>> = _homeStateFlow

    private val _soldoutStateFlow: MutableStateFlow<UiState<List<SoldOut>>> = MutableStateFlow(UiState.Loading)
    val soldoutStateFlow: StateFlow<UiState<List<SoldOut>>> = _soldoutStateFlow

    private val _termStateFlow: MutableStateFlow<Int> = MutableStateFlow(7)
    val termStateFlow: StateFlow<Int> = _termStateFlow

    fun getMain() {
        baseViewModelScope.launch {
           mainUsecase.getMainAPI()
                .onSuccess {
                    it.catch { e ->
                        catchError(e)
                    }
                    it.collect { data ->
                        _homeStateFlow.value = UiState.Success(data)
                        onSoldOutDayClicked(_termStateFlow.value)
                    }
                }.onError { e ->
                   catchError(e)
                }
        }
    }

    override fun onHotItemClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotItem(cardId))
        }
    }

    override fun onHotSellerItemClicked(userId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotSeller(userId))
        }
    }

    override fun onRecentNftItemClicked(nftId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToRecentNftItem(nftId))
        }
    }

    override fun onSoldOutItemClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToSoldOut(cardId))
        }
    }

    override fun onCollectionItemClicked(userId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToCollection(userId))
        }
    }

    override fun onSoldOutDayClicked(term: Int) {
        baseViewModelScope.launch {
            mainUsecase.getSoldOutAPI(term)
                .onSuccess {
                    it.catch { e ->
                        catchError(e)
                    }
                    it.collect { data ->
                        _soldoutStateFlow.value = UiState.Success(data)
                        _termStateFlow.value = term
                    }
                }.onError { e ->
                    catchError(e)
                }
        }
    }
}