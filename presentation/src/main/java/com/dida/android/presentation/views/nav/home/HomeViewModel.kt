package com.dida.android.presentation.views.nav.home

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.base.successOrNull
import com.dida.domain.flatMap
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.HomeAPI
import com.dida.domain.usecase.SoldOutAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeAPI: HomeAPI,
    private val soldOutAPI: SoldOutAPI
    ) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationEvent: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationEvent: SharedFlow<HomeNavigationAction> = _navigationEvent

    private val _homeState: MutableStateFlow<UiState<Home>> = MutableStateFlow(UiState.Loading)
    val homeState: StateFlow<UiState<Home>> = _homeState

    private val _soldoutState: MutableStateFlow<UiState<List<SoldOut>>> = MutableStateFlow(UiState.Loading)
    val soldoutState: StateFlow<UiState<List<SoldOut>>> = _soldoutState

    private val _termState: MutableStateFlow<Int> = MutableStateFlow(7)
    val termState: StateFlow<Int> = _termState

    fun getMain() {
        baseViewModelScope.launch {
            homeAPI()
               .onSuccess {
                    it.collect { data ->
                        _homeState.value = UiState.Success(data)
                        onSoldOutDayClicked(_termState.value)
                    } }
               .onError { e -> catchError(e)  }
        }
    }

    override fun onSoldOutDayClicked(term: Int) {
        baseViewModelScope.launch {
            soldOutAPI(term)
                .onSuccess {
                    it.collect { data ->
                        _soldoutState.value = UiState.Success(data)
                        _termState.value = term
                    } }
                .onError { e -> catchError(e) }
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
}