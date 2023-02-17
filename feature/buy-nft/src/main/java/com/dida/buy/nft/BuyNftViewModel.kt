package com.dida.buy.nft

import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.BuyNftAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BuyNftViewModel @Inject constructor(
    private val buyNftAPI: BuyNftAPI
) : BaseViewModel() {

    private val TAG = "BuyNftViewModel"

    private val _navigationEvent: MutableSharedFlow<BuyNftNavigationAction> =
        MutableSharedFlow<BuyNftNavigationAction>()
    val navigationEvent: SharedFlow<BuyNftNavigationAction> = _navigationEvent


    fun buyNft(password : String, nftId : Long){
        baseViewModelScope.launch {
            buyNftAPI(password, nftId)
                .onSuccess {}
                .onError { e -> catchError(e) }
        }
    }
}