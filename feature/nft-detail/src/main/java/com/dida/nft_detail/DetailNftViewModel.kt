package com.dida.nft_detail

import android.os.Handler
import android.os.Looper
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.actionhandler.CommunityWriteActionHandler
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
import com.dida.nft_detail.bottom.DetailOwnerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val detailNftAPI: DetailNftAPI,
    private val postLikeAPI: PostLikeAPI,
    private val postsCardCardIdAPI: PostsCardCardIdAPI,
    private val sellNftAPI: SellNftAPI,
    private val hideNftAPI: HideNftAPI
) : BaseViewModel(), DetailNftActionHandler, CommunityActionHandler, CommunityWriteActionHandler {

    private val TAG = "DetailNftViewModel"

    private val _moreEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val moreEvent: SharedFlow<Unit> = _moreEvent

    private val _navigationEvent: MutableSharedFlow<DetailNftNavigationAction> = MutableSharedFlow<DetailNftNavigationAction>()
    val navigationEvent: SharedFlow<DetailNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<UiState<DetailNFT>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<DetailNFT>> = _detailNftState

    private val _communityState: MutableStateFlow<List<Posts>> = MutableStateFlow(emptyList())
    val communityState: StateFlow<List<Posts>> = _communityState.asStateFlow()

    val detailOwnerTypeState: MutableStateFlow<DetailOwnerType> = MutableStateFlow(DetailOwnerType.ALL)
    fun getDetailNft(cardId : Long) {
        baseViewModelScope.launch {
            detailNftAPI(cardId)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _detailNftState.value = UiState.Success(it)
                    setDetailOwnerType(it)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    fun getCommunity(cardId: Long) {
        baseViewModelScope.launch {
            postsCardCardIdAPI(cardId = cardId)
                .onSuccess { _communityState.value = it }
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

    fun sellNft(payPwd : String, cardId: Long, price : Double){
        baseViewModelScope.launch {
            showLoading()
            sellNftAPI(payPwd,cardId,price)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToHome)
                }
                .onError { e -> catchError(e) }
        }
    }

    fun hideNft(cardId: Long){
        baseViewModelScope.launch {
            showLoading()
            hideNftAPI(cardId)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToBack)
                }
                .onError { e -> catchError(e) }
        }
    }

    private fun setDetailOwnerType(detailNFT: DetailNFT) {
        baseViewModelScope.launch {
            if (detailNFT.type == "MINE") {
                if (detailNFT.price == "NOT SALE") {
                    detailOwnerTypeState.emit(DetailOwnerType.MINE_AND_NOTSALE)
                } else {
                    detailOwnerTypeState.emit(DetailOwnerType.MINE_AND_SALE)
                }
            } else if(detailNFT.type == "NEED LOGIN"){
                detailOwnerTypeState.emit(DetailOwnerType.NOTLOGIN)
            } else{
                detailOwnerTypeState.emit(DetailOwnerType.NOTMINE)
            }
        }
    }

    override fun onCommunityMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToCommunity)
        }
    }

    override fun onUserProfileClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToUserProfile(userId = detailNftState.value.successOrNull()!!.userId))
        }
    }

    override fun onCommunityItemClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToItemCommunity(postId = postId))
        }
    }

    override fun onCommunityWriteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToCreateCommunity)
        }
    }

    // 클립 버튼으로 처리
    override fun onClipOrMoreClicked(postId: Long) {
    }
}
