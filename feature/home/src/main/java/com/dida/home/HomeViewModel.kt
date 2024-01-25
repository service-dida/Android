package com.dida.home

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.domain.flatMap
import com.dida.domain.main.model.HotMember
import com.dida.domain.main.model.Main
import com.dida.domain.main.model.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MainUseCase
import com.dida.domain.usecase.MemberFollowUseCase
import com.dida.domain.usecase.NftLikeUseCase
import com.dida.domain.usecase.SoldOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUseCase: MainUseCase,
    private val soldOutUseCase: SoldOutUseCase,
    private val nftLikeUseCase: NftLikeUseCase,
    private val memberFollowUseCase: MemberFollowUseCase
) : BaseViewModel(), HomeActionHandler, NftActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationEvent: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationEvent: SharedFlow<HomeNavigationAction> = _navigationEvent.asSharedFlow()

    private val _messageEvent: MutableSharedFlow<HomeMessageAction> = MutableSharedFlow<HomeMessageAction>()
    val messageEvent: SharedFlow<HomeMessageAction> = _messageEvent.asSharedFlow()

    private val _homeState: MutableStateFlow<UiState<Main>> = MutableStateFlow(UiState.Loading)
    val homeState: StateFlow<UiState<Main>> = _homeState.asStateFlow()

    private val _soldoutState: MutableStateFlow<UiState<List<SoldOut>>> = MutableStateFlow(UiState.Loading)
    val soldoutState: StateFlow<UiState<List<SoldOut>>> = _soldoutState.asStateFlow()

    private val _termState: MutableStateFlow<Int> = MutableStateFlow(7)
    val termState: StateFlow<Int> = _termState.asStateFlow()

    fun getHome() {
        baseViewModelScope.launch {
            _homeState.value = UiState.Loading
            soldOutUseCase(range = termState.value)
                .onSuccess { _soldoutState.value = UiState.Success(it) }
                .flatMap { mainUseCase() }
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _homeState.value = UiState.Success(it)
                }
                .onError { e -> catchError(e) }
        }
    }

    private fun getMain() {
        baseViewModelScope.launch {
            mainUseCase().onSuccess {
                _homeState.value = UiState.Success(it)
                dismissLoading()
            }.onError { e -> catchError(e) }
        }
    }

    override fun onSoldOutDayClicked(day: Int) {
        baseViewModelScope.launch {
            soldOutUseCase(day)
                .onSuccess {
                    _soldoutState.value = UiState.Success(it)
                    _termState.value = day
                }.onError { e -> catchError(e) }
        }
    }

    override fun onUserFollowClicked(user: HotMember) {
        baseViewModelScope.launch {
            showLoading()
            memberFollowUseCase(user.memberId)
                .onSuccess {
                    if (user.followed) _messageEvent.emit(HomeMessageAction.UserUnFollowMessage)
                    else _messageEvent.emit(HomeMessageAction.UserFollowMessage(user.memberName))
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

    override fun onAddCardClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToCreateCard)
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
            nftLikeUseCase(nftId)
                .onSuccess {
                    if (liked) {
                        _messageEvent.emit(HomeMessageAction.DeleteCardBookmarkMessage)
                    } else {
                        _messageEvent.emit(HomeMessageAction.AddCardBookmarkMessage)
                    }
                    getMain()
                }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCreateButtonClicked() {}
}
