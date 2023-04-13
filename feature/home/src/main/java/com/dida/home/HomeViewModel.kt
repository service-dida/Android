package com.dida.home

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.flatMap
import com.dida.domain.model.main.Home
import com.dida.domain.model.main.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.HomeAPI
import com.dida.domain.usecase.main.PostLikeAPI
import com.dida.domain.usecase.main.PostUserFollowAPI
import com.dida.domain.usecase.main.SoldOutAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeAPI: HomeAPI,
    private val soldOutAPI: SoldOutAPI,
    private val postLikeAPI: PostLikeAPI,
    private val postUserFollowAPI: PostUserFollowAPI,
) : BaseViewModel(), HomeActionHandler, NftActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationEvent: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationEvent: SharedFlow<HomeNavigationAction> = _navigationEvent.asSharedFlow()

    private val _messageEvent: MutableSharedFlow<HomeMessageAction> = MutableSharedFlow<HomeMessageAction>()
    val messageEvent: SharedFlow<HomeMessageAction> = _messageEvent.asSharedFlow()

    private val _homeState: MutableStateFlow<UiState<Home>> = MutableStateFlow(UiState.Loading)
    val homeState: StateFlow<UiState<Home>> = _homeState.asStateFlow()

    private val _soldoutState: MutableStateFlow<UiState<List<SoldOut>>> = MutableStateFlow(UiState.Loading)
    val soldoutState: StateFlow<UiState<List<SoldOut>>> = _soldoutState.asStateFlow()

    private val _termState: MutableStateFlow<Int> = MutableStateFlow(7)
    val termState: StateFlow<Int> = _termState.asStateFlow()

    fun getHome() {
        baseViewModelScope.launch {
            soldOutAPI.invoke(term = termState.value)
                .onSuccess { _soldoutState.value = UiState.Success(it) }
                .flatMap { homeAPI() }
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _homeState.value = UiState.Success(it) }
                .onError { e -> catchError(e) }
        }
    }

    private fun getMain() {
        baseViewModelScope.launch {
            homeAPI()
                .onSuccess {
                    _homeState.value = UiState.Success(it)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    override fun onSoldOutDayClicked(term: Int) {
        baseViewModelScope.launch {
            soldOutAPI(term)
                .onSuccess {
                    _soldoutState.value = UiState.Success(it)
                    _termState.value = term }
                .onError { e -> catchError(e) }
        }
    }

    override fun onUserFollowClicked(user: com.dida.domain.model.main.Collection) {
        baseViewModelScope.launch {
            showLoading()
            postUserFollowAPI(user.userId)
                .onSuccess {
                    if (user.follow) _messageEvent.emit(HomeMessageAction.UserUnFollowMessage)
                    else _messageEvent.emit(HomeMessageAction.UserFollowMessage(user.userName))
                    getMain()
                }
                .onError { e -> catchError(e) }
        }
    }

    override fun onHotSellerMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotSellerMore)
        }
    }

    override fun onSoldOutMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToSoldOutMore)
        }
    }

    override fun onRecentMoreNftClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToRecentNftMore)
        }
    }

    override fun onCollectionMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToCollectionMore)
        }
    }

    override fun onHotItemClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotItem(cardId))
        }
    }

    override fun onHotSellerItemClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotSeller(userId))
        }
    }

    override fun onSoldOutItemClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToSoldOut(cardId))
        }
    }

    override fun onCollectionItemClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToCollection(userId))
        }
    }

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToRecentNftItem(nftId))
        }
    }

    override fun onLikeBtnClicked(nftId: Long, liked: Boolean) {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(nftId)
                .onSuccess {
                    if (liked) _messageEvent.emit(HomeMessageAction.DeleteCardBookmarkMessage)
                    else _messageEvent.emit(HomeMessageAction.AddCardBookmarkMessage)
                    getMain()
                }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }
}
