package com.dida.buy.nft.success

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.SellNftAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuySuccessViewModel @Inject constructor(
    private val sellNftAPI: SellNftAPI
) : BaseViewModel() , BuySuccessActionHandler {

    private val TAG = "BuyNftSuccessViewModel"

    private val _navigationEvent: MutableSharedFlow<BuySuccessNavigationAction> =
        MutableSharedFlow<BuySuccessNavigationAction>()
    val navigationEvent: SharedFlow<BuySuccessNavigationAction> = _navigationEvent

    fun sellNft(payPwd: String, cardId: Long, price: Double) {
        baseViewModelScope.launch {
            showLoading()
            sellNftAPI(payPwd, cardId, price)
                .onSuccess {
                    _navigationEvent.emit(BuySuccessNavigationAction.NavigateToMypage)
                }
                .onError { e -> catchError(e) }
        }
    }

    override fun onSaleClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(BuySuccessNavigationAction.NavigateToSaleNft)
        }
    }

    override fun onMypageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(BuySuccessNavigationAction.NavigateToMypage)
        }
    }
}