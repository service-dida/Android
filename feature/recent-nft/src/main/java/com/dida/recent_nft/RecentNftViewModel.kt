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

    private val _navigateToNftDetail: MutableSharedFlow<Long> = MutableSharedFlow<Long>()
    val navigateToNftDetail: SharedFlow<Long> = _navigateToNftDetail.asSharedFlow()

    private val _recentNftState: MutableStateFlow<Contents<RecentNft>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, hasNext = true, content = emptyList())
    )
    val recentNftState: StateFlow<Contents<RecentNft>> = _recentNftState.asStateFlow()

    init {
        baseViewModelScope.launch {
            recentNftsUseCase(page = INIT_PAGE, size = PAGE_SIZE)
                .onSuccess { _recentNftState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onNftItemClicked(nftId: Long) {
        baseViewModelScope.launch {
            _navigateToNftDetail.emit(nftId)
        }
    }

    override fun onLikeBtnClicked(nftId: Long, liked: Boolean) {
        baseViewModelScope.launch {
            showLoading()
            nftLikeUseCase(nftId)
                .onSuccess {
                    val newList = recentNftState.value.content.toMutableList()
                    val nftIndex = recentNftState.value.content.indexOfFirst { it.nftId == nftId }
                    val beforeNft = newList[nftIndex]
                    val newNft = RecentNft(
                        nftId = beforeNft.nftId,
                        nftName = beforeNft.nftName,
                        memberName = beforeNft.memberName,
                        nftImgUrl = beforeNft.nftImgUrl,
                        price = beforeNft.price,
                        liked = !beforeNft.liked
                    )
                    newList[nftIndex] = newNft
                    _recentNftState.value = Contents(page = recentNftState.value.page, pageSize = recentNftState.value.pageSize, hasNext = recentNftState.value.hasNext, content = newList)
                }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCreateButtonClicked() {}

    fun nextPage() {
        baseViewModelScope.launch {
            if (!recentNftState.value.hasNext) return@launch
            recentNftsUseCase(recentNftState.value.page + 1, PAGE_SIZE)
                .onSuccess { _recentNftState.value = it }
                .onError { e -> catchError(e) }
        }
    }
}
