package com.dida.nft_detail

import android.os.Handler
import android.os.Looper
import com.dida.common.util.CommunityActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.CommunityWriteActionHandler
import com.dida.common.util.UiState
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.DetailNftAPI
import com.dida.domain.usecase.main.PostLikeAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val detailNftAPI: DetailNftAPI,
    private val postLikeAPI: PostLikeAPI
) : BaseViewModel(), DetailNftActionHandler, CommunityActionHandler, CommunityWriteActionHandler {

    private val TAG = "DetailNftViewModel"

    private val _myWriteState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val myWriteState: StateFlow<Boolean> = _myWriteState

    private val _moreEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val moreEvent: SharedFlow<Unit> = _moreEvent

    private val _navigationEvent: MutableSharedFlow<DetailNftNavigationAction> = MutableSharedFlow<DetailNftNavigationAction>()
    val navigationEvent: SharedFlow<DetailNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<UiState<DetailNFT>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<DetailNFT>> = _detailNftState

    fun getDetailNft(cardId : Long) {
        baseViewModelScope.launch {
            detailNftAPI(cardId)
                .onSuccess {
                    Handler(Looper.getMainLooper()).postDelayed({
                        _detailNftState.value = UiState.Success(it)
                    },500)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    fun postlikeNft(cardId : Long){
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(cardId)
                .onSuccess { getDetailNft(cardId) }
                .onError { e -> catchError(e) }
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

    override fun onCommunityWriteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToCreateCommunity)
        }
    }

    // 클립 버튼으로 처리
    override fun onClipOrMoreClicked(communityId: Int) {
    }
}