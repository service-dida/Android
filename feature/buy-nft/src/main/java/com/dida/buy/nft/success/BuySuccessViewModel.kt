package com.dida.buy.nft.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.SellNftUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BuySuccessViewModel @AssistedInject constructor(
    @Assisted("cardId") val cardId: Long,
    private val sellNftUseCase: SellNftUseCase
) : BaseViewModel() , BuySuccessActionHandler {

    private val TAG = "BuyNftSuccessViewModel"

    private val _navigationEvent: MutableSharedFlow<BuySuccessNavigationAction> =
        MutableSharedFlow<BuySuccessNavigationAction>()
    val navigationEvent: SharedFlow<BuySuccessNavigationAction> = _navigationEvent

    fun sellNft(payPwd: String, price: Double) {
        baseViewModelScope.launch {
            showLoading()
            sellNftUseCase(payPwd, cardId, price)
                .onSuccess { _navigationEvent.emit(BuySuccessNavigationAction.NavigateToMypage) }
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

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("cardId") cardId: Long
        ): BuySuccessViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            cardId: Long,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(cardId) as T
            }
        }
    }
}
