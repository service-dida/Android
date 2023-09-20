package com.dida.nft_detail

import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.actionhandler.CommunityWriteActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.ui.report.ReportViewModelDelegate
import com.dida.common.util.NoCompareMutableStateFlow
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.Nft
import com.dida.domain.main.model.Post
import com.dida.domain.main.model.Report
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.BlockUseCase
import com.dida.domain.usecase.DeleteNftUseCase
import com.dida.domain.usecase.NftDetailUseCase
import com.dida.domain.usecase.NftLikeUseCase
import com.dida.domain.usecase.PostsFromNftUseCase
import com.dida.domain.usecase.SellNftUseCase
import com.dida.nft_detail.bottom.DetailOwnerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val nftDetailUseCase: NftDetailUseCase,
    private val nftLikeUseCase: NftLikeUseCase,
    private val postsFromNftUseCase: PostsFromNftUseCase,
    private val sellNftUseCase: SellNftUseCase,
    private val blockUseCase: BlockUseCase,
    private val deleteNftUseCase: DeleteNftUseCase,
    reportViewModelDelegate: ReportViewModelDelegate
) : BaseViewModel(), DetailNftActionHandler, CommunityActionHandler, CommunityWriteActionHandler,
    ReportViewModelDelegate by reportViewModelDelegate {

    private val TAG = "DetailNftViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailNftNavigationAction> = MutableSharedFlow<DetailNftNavigationAction>()
    val navigationEvent: SharedFlow<DetailNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<UiState<Nft>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<Nft>> = _detailNftState

    private val _communityState: MutableStateFlow<List<Post>> = MutableStateFlow(emptyList())
    val communityState: StateFlow<List<Post>> = _communityState.asStateFlow()

    val detailOwnerTypeState: NoCompareMutableStateFlow<DetailOwnerType> = NoCompareMutableStateFlow(DetailOwnerType.ALL)

    private val cardIdState: MutableStateFlow<Long> = MutableStateFlow(0)

    fun setCardId(cardId: Long) {
        cardIdState.value = cardId
        onGetDetailCard()
        onGetCommunity()
    }

    private fun onGetDetailCard() {
        baseViewModelScope.launch {
            nftDetailUseCase(cardIdState.value)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _detailNftState.value = UiState.Success(it)
                    setDetailOwnerType(it)
                    dismissLoading()
                }.onError { e -> catchError(e) }
        }
    }

    private fun onGetCommunity() {
        baseViewModelScope.launch {
            postsFromNftUseCase(nftId = cardIdState.value, page = 0, size = 10)
                .onSuccess { _communityState.value = it.content }
                .onError { e -> catchError(e) }
        }
    }

    fun onLikePost() {
        baseViewModelScope.launch {
            showLoading()
            nftLikeUseCase(cardIdState.value)
                .onSuccess { onGetDetailCard() }
                .onError { e -> catchError(e) }
        }
    }

    fun onSellCard(payPwd: String, price: Double) {
        baseViewModelScope.launch {
            showLoading()
            sellNftUseCase(payPwd, cardIdState.value, price)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToHome)
                }.onError { e -> catchError(e) }
        }
    }

    fun onHideCard() {
        baseViewModelScope.launch {
            showLoading()
            blockUseCase(type = Block.NFT, blockId = cardIdState.value)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToBack)
                }.onError { e -> catchError(e) }
        }
    }

    // TODO : NFT 삭제 관련 로직 수정하기
    fun deleteNft(password : String) {
        baseViewModelScope.launch {
            showLoading()
            deleteNftUseCase(cardIdState.value)
                .onSuccess {
                    _navigationEvent.emit(DetailNftNavigationAction.NavigateToHome)
                }.onError { e -> catchError(e) }
        }
    }

    fun onReport(type: Report, reportId: Long, content: String) {
        onReportDelegate(coroutineScope = baseViewModelScope, type = type, reportId = reportId, content = content)
    }

    // TODO : NFT 상세 내껀지 판별하는 로직 수정
    private fun setDetailOwnerType(detailNFT: Nft) {
//        baseViewModelScope.launch {
//            if (detailNFT.nftInfo.type == "MINE") {
//                if (detailNFT.price == "NOT SALE") {
//                    detailOwnerTypeState.emit(DetailOwnerType.MINE_AND_NOTSALE)
//                } else {
//                    detailOwnerTypeState.emit(DetailOwnerType.MINE_AND_SALE)
//                }
//            } else if (detailNFT.type == "NOT MINE") {
//                if (detailNFT.price == "NOT SALE") {
//                    detailOwnerTypeState.emit(DetailOwnerType.NOTMINE_AND_NOTSALE)
//                } else {
//                    detailOwnerTypeState.emit(DetailOwnerType.NOTMINE_AND_SALE)
//                }
//            } else if (detailNFT.type == "NEED LOGIN") {
//                if (detailNFT.price == "NOT SALE") {
//                    detailOwnerTypeState.emit(DetailOwnerType.NOTLOGIN_AND_NOTSALE)
//                } else {
//                    detailOwnerTypeState.emit(DetailOwnerType.NOTLOGIN_AND_SALE)
//                }
//            }
//        }
    }

    override fun onCommunityMoreClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToCommunity)
        }
    }

    override fun onUserProfileClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToUserProfile(userId = detailNftState.value.successOrNull()?.memberInfo?.memberId ?: 0))
        }
    }

    // TODO : Remote Exception 관련 수정하기
    override fun onNextButtonClicked() {
        baseViewModelScope.launch {
            when (detailOwnerTypeState.value) {
                DetailOwnerType.NOTLOGIN_AND_SALE -> {
//                    catchError(HaveNotJwtTokenException(Throwable(), "", 100))
                }

                DetailOwnerType.NOTMINE_AND_SALE -> {
                    val detailNFT = detailNftState.value.successOrNull()
                    if (detailNFT != null) {
//                        _navigationEvent.emit(
//                            DetailNftNavigationAction.NavigateToBuyNft(
//                                detailNFT.nftInfo.nftId,
//                                detailNFT.nftInfo.nftImgUrl,
//                                detailNFT.nftInfo.nftName,
//                                detailNFT.memberInfo.profileImgUrl ?: "",
//                                detailNFT.memberInfo.memberName,
//                                detailNFT.nftInfo.price,
//                                detailNFT.viewerNickname,
//                                detailNFT.marketId
//                            )
//                        )
                    }
                }

                DetailOwnerType.MINE_AND_NOTSALE -> _navigationEvent.emit(DetailNftNavigationAction.NavigateToSell)

                else -> {}
            }
        }
    }

    // TODO : 추후 Contract 주소 & 소유권 내역 관련 추가 & NFT 글쓰러 가기 내용 추가
    override fun onContractLinkClicked() = Unit

    override fun onOwnerShipClicked() = Unit
    override fun onWritePostClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailNftNavigationAction.NavigateToWritePost)
        }
    }

    override fun onImageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                DetailNftNavigationAction.NavigateToImageDetail(
                    imageUrl = detailNftState.value.successOrNull()?.nftInfo?.nftImgUrl,
                    imageTitle = detailNftState.value.successOrNull()?.nftInfo?.nftName,
                    imageDescription = detailNftState.value.successOrNull()?.description
                )
            )
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
}
