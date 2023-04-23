package com.dida.buy.nft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.DetailNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.BuyNftAPI
import com.dida.domain.usecase.main.WalletAmountAPI
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BuyNftViewModel @AssistedInject constructor(
    @Assisted("buyCard") val buyCard: BuyCard,
    private val buyNftAPI: BuyNftAPI,
    private val amountAPI: WalletAmountAPI
) : BaseViewModel() {

    private val TAG = "BuyNftViewModel"

    private val _navigationEvent: MutableSharedFlow<BuyNftNavigationAction> = MutableSharedFlow<BuyNftNavigationAction>()
    val navigationEvent: SharedFlow<BuyNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<DetailNft?> = MutableStateFlow(null)
    val detailNftState: StateFlow<DetailNft?> = _detailNftState

    init {
        baseViewModelScope.launch {
            _detailNftState.emit(
                DetailNft(cardId = buyCard.cardId, contracts = "", description = "", id = "", imgUrl = buyCard.imgUrl, viewerNickname = buyCard.viewerNickname,
                    nickname = buyCard.nickname, price = buyCard.price, profileUrl = buyCard.profileUrl, title = buyCard.title,
                    liked = false, type = "", userId = 0L, marketId = 0L
                )
            )
        }
    }

    fun buyNft(password: String) {
        baseViewModelScope.launch {
            showLoading()
            amountAPI()
                .onSuccess {
                    if (it.dida >= buyCard.price.toDouble()) {
                        buyNftAPI(password, buyCard.marketId)
                            .onSuccess { _navigationEvent.emit(BuyNftNavigationAction.NavigateToSuccess(buyCard.cardId)) }
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

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("buyCard") buyCard: BuyCard
        ): BuyNftViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            buyCard: BuyCard
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(buyCard) as T
            }
        }
    }
}

data class BuyCard(
    val cardId: Long,
    val imgUrl: String,
    val title: String,
    val profileUrl: String,
    val nickname: String,
    val price: String,
    val viewerNickname: String,
    val marketId: Long
)
