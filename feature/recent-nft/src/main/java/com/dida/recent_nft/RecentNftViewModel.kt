package com.dida.recent_nft

import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.INIT_PAGE
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.RecentNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.NftLikeUseCase
import com.dida.domain.usecase.RecentNftsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentNftViewModel @Inject constructor(
    private val recentNftsUseCase: RecentNftsUseCase,
    private val nftLikeUseCase: NftLikeUseCase
) : BaseViewModel(), RecentNftActionHandler, NftActionHandler {

    private val TAG = "RecentNftViewModel"

    private val _navigationEvent: MutableSharedFlow<RecentNftNavigationAction> = MutableSharedFlow<RecentNftNavigationAction>()
    val navigationEvent: SharedFlow<RecentNftNavigationAction> = _navigationEvent.asSharedFlow()

    private val _cardsState: MutableStateFlow<Contents<RecentNft>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, hasNext = true, content = emptyList())
    )
    val cardsState: StateFlow<Contents<RecentNft>> = _cardsState.asStateFlow()

    init {
        baseViewModelScope.launch {
            recentNftsUseCase(page = INIT_PAGE, size = PAGE_SIZE)
                .onSuccess { _cardsState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(RecentNftNavigationAction.NavigateToRecentNftItem(nftId))
        }
    }

    override fun onLikeBtnClicked(nftId: Long, liked: Boolean) {
        baseViewModelScope.launch {
            showLoading()
            nftLikeUseCase(nftId)
                .onSuccess { _navigationEvent.emit(RecentNftNavigationAction.NavigateToCardRefresh) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCreateButtonClicked() {}

    fun nextPage() {
        baseViewModelScope.launch {
            if (!cardsState.value.hasNext) return@launch
            recentNftsUseCase(cardsState.value.page + 1, PAGE_SIZE)
                .onSuccess { _cardsState.value = it }
                .onError { e -> catchError(e) }
        }
    }
}
