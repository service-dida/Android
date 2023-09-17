package com.dida.wallet

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGING_SIZE
import com.dida.domain.main.model.DealingHistory
import com.dida.domain.main.model.Wallet
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MemberWalletUseCase
import com.dida.domain.usecase.PurchaseTransactionsUseCase
import com.dida.domain.usecase.SaleTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val memberWalletUseCase: MemberWalletUseCase,
    private val purchaseTransactionsUseCase: PurchaseTransactionsUseCase,
    private val saleTransactionsUseCase: SaleTransactionsUseCase,
) : BaseViewModel(), WalletActionHandler {

    private val TAG = "WalletViewModel"

    private val _navigationEvent: MutableSharedFlow<WalletNavigationAction> = MutableSharedFlow<WalletNavigationAction>()
    val navigationEvent: SharedFlow<WalletNavigationAction> = _navigationEvent

    private val _currentHistoryState: MutableStateFlow<List<DealingHistory>> = MutableStateFlow<List<DealingHistory>>(emptyList())
    val currentHistoryState: StateFlow<List<DealingHistory>> = _currentHistoryState.asStateFlow()

    private val _walletListState: MutableStateFlow<List<Wallet>> = MutableStateFlow(emptyList())
    val walletListState: StateFlow<List<Wallet>> = _walletListState.asStateFlow()

    private val _walletAddressState: MutableStateFlow<String> = MutableStateFlow("")
    val walletAddressState: StateFlow<String> = _walletAddressState.asStateFlow()



    /** NFT 거래 내역 타입 LiveData
     * 0 : 전체
     * 1 : 구매 <- type = true
     * 2 : 판매 <- type = false
     */
    private val _typeHistoryState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val typeHistoryState: StateFlow<Int> = _typeHistoryState.asStateFlow()

    init {
        baseViewModelScope.launch {
            memberWalletUseCase()
                .onSuccess {
                    _walletListState.value = listOf(
                        Wallet(amount = it.dida.toString(), type = "DIDA"),
                        Wallet(amount = it.klay.toString(), type = "KLAY")
                    )
                    _walletAddressState.value = it.address
                }.onError { e -> catchError(e) }
        }
    }

    fun getWallet() {
        baseViewModelScope.launch {
            launch {
                memberWalletUseCase()
                    .onSuccess {
                        _walletListState.value = listOf(
                            Wallet(amount = it.dida.toString(), type = "DIDA"),
                            Wallet(amount = it.klay.toString(), type = "KLAY")
                        )
                        _walletAddressState.value = it.address
                    }.onError { e -> catchError(e) }
            }

            launch {
                when (typeHistoryState.value) {
                    0 -> historyAll()
                    1 -> historyBuy()
                    2 -> historySell()
                }
            }
        }
    }

    override fun onHistoryTypeClicked(type: Int) {
        baseViewModelScope.launch {
            _typeHistoryState.value = type
            getHistory()
        }
    }

    fun getHistory(){
        when(typeHistoryState.value) {
            0 -> historyAll()
            1 -> historyBuy()
            2 -> historySell()
        }
    }

    // TODO : 구매, 판매 내역 관련 로직 수정하기
    fun historyAll() {
//        baseViewModelScope.launch {
//            buySellListAPI()
//                .onSuccess { _currentHistoryState.value = it }
//                .onError { e -> catchError(e) }
//        }
    }

    fun historyBuy() {
        baseViewModelScope.launch {
            purchaseTransactionsUseCase(page = 0, size = PAGING_SIZE)
                .onSuccess { _currentHistoryState.value = it.content }
                .onError { e -> catchError(e) }
        }
    }

    fun historySell() {
        baseViewModelScope.launch {
            saleTransactionsUseCase(0, PAGING_SIZE)
                .onSuccess { _currentHistoryState.value = it.content }
                .onError { e -> catchError(e) }
        }
    }

    override fun onSwapHistoryClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToSwapHistory)
        }
    }

    override fun onNftItemClicked(cardId: Long) {
        //TODO : 추후에 클릭 상황에 따라 구현해야함
    }

    override fun onHotCardButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToHotCard)
        }
    }

    fun onBack() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToBack)
        }
    }
}
