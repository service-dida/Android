package com.dida.nft_detail

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.actionhandler.CommunityWriteActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.ui.report.ReportType
import com.dida.common.ui.report.ReportViewModelDelegate
import com.dida.common.util.NoCompareMutableStateFlow
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.data.model.HaveNotJwtTokenException
import com.dida.domain.model.main.DetailNft
import com.dida.domain.model.main.Posts
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
import com.dida.nft_detail.bottom.DetailOwnerType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailNftViewModel @AssistedInject constructor(
    @Assisted("cardId") val cardId: Long,
    private val detailNftAPI: DetailNftAPI,
    private val postLikeAPI: PostLikeAPI,
    private val postsCardCardIdAPI: PostsCardCardIdAPI,
    private val sellNftAPI: SellNftAPI,
    private val hideNftAPI: HideNftAPI,
    private val deleteNftAPI: DeleteNftAPI,
    private val reportViewModelDelegate: ReportViewModelDelegate
) : BaseViewModel(), DetailNftActionHandler, CommunityActionHandler, CommunityWriteActionHandler,
    ReportViewModelDelegate by reportViewModelDelegate {

    private val TAG = "DetailNftViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailNftNavigationAction> = MutableSharedFlow<DetailNftNavigationAction>()
    val navigationEvent: SharedFlow<DetailNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<UiState<DetailNft>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<DetailNft>> = _detailNftState

    private val _communityState: MutableStateFlow<List<Posts>> = MutableStateFlow(emptyList())
    val communityState: StateFlow<List<Posts>> = _communityState.asStateFlow()

    val detailOwnerTypeState: NoCompareMutableStateFlow<DetailOwnerType> = NoCompareMutableStateFlow(DetailOwnerType.ALL)

    init {
        onGetDetailCard()
        onGetCommunity()
    }

    private fun onGetDetailCard() {
        baseViewModelScope.launch {
            detailNftAPI(cardId)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _detailNftState.value = UiState.Success(it)
                    setDetailOwnerType(it)
                    dismissLoading()
                }
                .onError { e -> catchError(e) }
        }
    }

    private fun onGetCommunity() {
        baseViewModelScope.launch {
            postsCardCardIdAPI(cardId = cardId)
                .onSuccess { _communityState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun onLikePost() {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(cardId)
                .onSuccess { onGetDetailCard() }
                .onError { e -> catchError(e) }
        }
    }

    fun onSellCard(payPwd: String, price: Double) {
        baseViewModelScope.launch {
            showLoading()
            sellNftAPI(payPwd, cardId, price)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToHome)
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onHideCard() {
        baseViewModelScope.launch {
            showLoading()
            hideNftAPI(cardId)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToBack)
                }
                .onError { e -> catchError(e) }
        }
    }

    fun deleteNft(password : String) {
        baseViewModelScope.launch {
            showLoading()
            deleteNftAPI(cardId, password)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToHome)
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onReport(type: ReportType, reportId: Long, content: String) {
        onReportDelegate(coroutineScope = viewModelScope, type = type, reportId = reportId, content = content)
    }

    private fun setDetailOwnerType(detailNFT: DetailNft) {
        baseViewModelScope.launch {
            if (detailNFT.type == "MINE") {
                if (detailNFT.price == "NOT SALE") {
                    detailOwnerTypeState.emit(DetailOwnerType.MINE_AND_NOTSALE)
                } else {
                    detailOwnerTypeState.emit(DetailOwnerType.MINE_AND_SALE)
                }
            } else if (detailNFT.type == "NOT MINE") {
                if (detailNFT.price == "NOT SALE") {
                    detailOwnerTypeState.emit(DetailOwnerType.NOTMINE_AND_NOTSALE)
                } else {
                    detailOwnerTypeState.emit(DetailOwnerType.NOTMINE_AND_SALE)
                }
            } else if (detailNFT.type == "NEED LOGIN") {
                if (detailNFT.price == "NOT SALE") {
                    detailOwnerTypeState.emit(DetailOwnerType.NOTLOGIN_AND_NOTSALE)
                } else {
                    detailOwnerTypeState.emit(DetailOwnerType.NOTLOGIN_AND_SALE)
                }
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

    override fun onNextButtonClicked() {
        baseViewModelScope.launch {
            when(detailOwnerTypeState.value){
                DetailOwnerType.NOTLOGIN_AND_SALE ->{
                    catchError(HaveNotJwtTokenException(Throwable(), "", 100))
                }
                DetailOwnerType.NOTMINE_AND_SALE ->{
                    val detailNFT = detailNftState.value.successOrNull()
                    if(detailNFT != null){
                        _navigationEvent.emit(
                            DetailNftNavigationAction.NavigateToBuyNft(
                                detailNFT.cardId,
                                detailNFT.imgUrl,
                                detailNFT.title,
                                detailNFT.profileUrl,
                                detailNFT.nickname,
                                detailNFT.price,
                                detailNFT.viewerNickname,
                                detailNFT.marketId
                            )
                        )
                    }
                }
                DetailOwnerType.MINE_AND_NOTSALE ->{
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToSell)
                }
                else -> {}
            }
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

    // TODO : 신고 및 차단 & 수정 삭제 플로우 추가하기
    override fun onReportClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToReport(userId))
        }
    }

    override fun onBlockClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToBlock(userId))
        }
    }

    override fun onUpdateClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToUpdate(postId))
        }
    }

    override fun onDeleteClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToDelete(postId))
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("cardId") cardId: Long
        ): DetailNftViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            cardId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(cardId) as T
            }
        }
    }
}
