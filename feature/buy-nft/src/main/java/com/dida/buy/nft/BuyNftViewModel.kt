package com.dida.buy.nft

import com.dida.common.base.BaseViewModel
import com.dida.domain.flatMap
import com.dida.domain.model.main.DetailNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.BuyNftAPI
import com.dida.domain.usecase.main.CreateWalletAPI
import com.dida.domain.usecase.main.WalletAmountAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyNftViewModel @Inject constructor(
    private val buyNftAPI: BuyNftAPI,
    private val amountAPI: WalletAmountAPI
) : BaseViewModel() {

    private val TAG = "BuyNftViewModel"

    private val _navigationEvent: MutableSharedFlow<BuyNftNavigationAction> =
        MutableSharedFlow<BuyNftNavigationAction>()
    val navigationEvent: SharedFlow<BuyNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<DetailNft?> = MutableStateFlow(null)
    val detailNftState: StateFlow<DetailNft?> = _detailNftState

    private var nftPrice = 0.0

    fun initDetailNft(
        cardId: Long,
        imgUrl: String,
        title: String,
        profileUrl: String,
        nickname: String,
        price: String,
        viewerNickname: String
    ) {
        baseViewModelScope.launch {
            _detailNftState.emit(
                DetailNft(
                    cardId,
                    "",
                    "",
                    "",
                    imgUrl,
                    viewerNickname,
                    nickname,
                    price,
                    profileUrl,
                    title,
                    false,
                    "",
                    0L,
                    0L
                )
            )
        }
    }

    fun buyNft(password: String, marketId: Long, price: String) {
        baseViewModelScope.launch {
            showLoading()
            amountAPI()
                .onSuccess {
                    if (it.dida >= price.toDouble()) {
                        buyNftAPI(password, marketId)
                            .onSuccess {
                                _navigationEvent.emit(BuyNftNavigationAction.NavigateToSuccess)
                            }
                            .onError { e -> catchError(e) }
                        dismissLoading()
                    } else {
                        _navigationEvent.emit(BuyNftNavigationAction.NavigateToFailAlert)
                    }
                }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }
}