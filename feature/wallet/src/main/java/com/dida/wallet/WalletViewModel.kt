package com.dida.wallet

import com.dida.common.base.BaseViewModel
import com.dida.domain.flatMap
import com.dida.domain.model.nav.mypage.BuySellList
import com.dida.domain.model.nav.mypage.WalletCardHolderModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.BuyListAPI
import com.dida.domain.usecase.main.BuySellListAPI
import com.dida.domain.usecase.main.WalletAmountAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletAmountAPI: WalletAmountAPI,
    private val buySellListAPI: BuySellListAPI,
    private val buyListAPI: BuyListAPI,
    private val sellListAPI: BuySellListAPI
) : BaseViewModel(), WalletActionHandler {

    private val TAG = "WalletViewModel"

    private val _navigationEvent: MutableSharedFlow<WalletNavigationAction> = MutableSharedFlow<WalletNavigationAction>()
    val navigationEvent: SharedFlow<WalletNavigationAction> = _navigationEvent

    private val _currentHistoryState: MutableStateFlow<List<BuySellList>> = MutableStateFlow<List<BuySellList>>(emptyList())
    val currentHistoryState: StateFlow<List<BuySellList>> = _currentHistoryState.asStateFlow()

    private val _walletListState: MutableStateFlow<List<WalletCardHolderModel>> = MutableStateFlow(emptyList())
    val walletListState: StateFlow<List<WalletCardHolderModel>> = _walletListState.asStateFlow()



    /** NFT 거래 내역 타입 LiveData
     * 0 : 전체
     * 1 : 구매 <- type = true
     * 2 : 판매 <- type = false
     */
    private val _typeHistoryState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val typeHistoryState: StateFlow<Int> = _typeHistoryState.asStateFlow()

    init {
        baseViewModelScope.launch {
            walletAmountAPI()
                .onSuccess {
                    _walletListState.value = listOf(
                        WalletCardHolderModel(amount = it.dida.toString(), type = "DIDA"),
                        WalletCardHolderModel(amount = it.klay.toString(), type = "KLAY")
                    ) }
                .flatMap { buySellListAPI() }
                .onSuccess {  }
                .onError { e -> catchError(e) }
        }
    }

    override fun onHistoryTypeClicked(type: Int) {
        baseViewModelScope.launch {
            _typeHistoryState.value = type
            when(type) {
                0 -> historyAll()
                1 -> historyBuy()
                2 -> historySell()
            }
        }
    }

    fun historyAll() {
        baseViewModelScope.launch {
            buySellListAPI()
                .onSuccess { _currentHistoryState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun historyBuy() {
        baseViewModelScope.launch {
            buyListAPI()
                .onSuccess { _currentHistoryState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun historySell() {
        baseViewModelScope.launch {
            sellListAPI()
                .onSuccess { _currentHistoryState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onSwapHistoryClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToSwapHistory)
        }
    }

    fun onBack() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToBack)
        }
    }
}
