package com.dida.recent_nft

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.UserNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostLikeAPI
import com.dida.domain.usecase.main.RecentCardAPI
import com.dida.recent_nft.adapter.createCardPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentNftViewModel @Inject constructor(
    private val recentCardAPI: RecentCardAPI,
    private val postLikeAPI: PostLikeAPI
) : BaseViewModel(), RecentNftActionHandler, NftActionHandler {

    private val TAG = "RecentNftViewModel"

    private val _navigationEvent: MutableSharedFlow<RecentNftNavigationAction> = MutableSharedFlow<RecentNftNavigationAction>()
    val navigationEvent: SharedFlow<RecentNftNavigationAction> = _navigationEvent.asSharedFlow()

    val cardsState: Flow<PagingData<UserNft>> = createCardPager(recentCardAPI = recentCardAPI).flow.cachedIn(baseViewModelScope)

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(RecentNftNavigationAction.NavigateToRecentNftItem(nftId))
        }
    }

    override fun onLikeBtnClicked(nftId: Long, liked: Boolean) {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(nftId)
                .onSuccess { _navigationEvent.emit(RecentNftNavigationAction.NavigateToCardRefresh) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }
}
