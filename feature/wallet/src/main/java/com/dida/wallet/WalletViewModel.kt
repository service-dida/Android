package com.dida.wallet

import com.dida.common.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.mypage.WalletCardHolderModel
import com.dida.domain.model.nav.mypage.WalletNFTHistoryHolderModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.WalletAmountAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletAmountAPI: WalletAmountAPI
) : BaseViewModel(), WalletActionHandler {

    private val TAG = "WalletViewModel"

    private val _navigationEvent: MutableSharedFlow<WalletNavigationAction> = MutableSharedFlow<WalletNavigationAction>()
    val navigationEvent: SharedFlow<WalletNavigationAction> = _navigationEvent

    private val _walletHistoryState: MutableStateFlow<List<WalletNFTHistoryHolderModel>> = MutableStateFlow<List<WalletNFTHistoryHolderModel>>(emptyList())

    private val _currentHistoryState: MutableStateFlow<List<WalletNFTHistoryHolderModel>> = MutableStateFlow<List<WalletNFTHistoryHolderModel>>(emptyList())
    val currentHistoryState: StateFlow<List<WalletNFTHistoryHolderModel>> = _currentHistoryState

    private val _walletListState: MutableStateFlow<List<WalletCardHolderModel>> = MutableStateFlow(emptyList())
    val walletListState: StateFlow<List<WalletCardHolderModel>> = _walletListState.asStateFlow()



    /** NFT 거래 내역 타입 LiveData
     * 0 : 전체
     * 1 : 구매 <- type = true
     * 2 : 판매 <- type = false
     */
    private val _nftTypeState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val nftTypeState: StateFlow<Int> = _nftTypeState

    init {
        baseViewModelScope.launch {
            walletAmountAPI()
                .onSuccess {
                    _walletListState.value = listOf(
                        WalletCardHolderModel(amount = it.dida.toString(), type = "DIDA"),
                        WalletCardHolderModel(amount = it.klay.toString(), type = "KLAY")
                    )
                }
                .onError { e -> catchError(e) }
        }
    }

    fun setNftHistory(request: List<WalletNFTHistoryHolderModel>) {
        _walletHistoryState.value = request
        _currentHistoryState.value = request
    }

    override fun onNftHistoryClicked(type: Int) {
        _nftTypeState.value = type
        when(type) {
            0 -> { _currentHistoryState.value = _walletHistoryState.value }
            1 -> { _currentHistoryState.value = _walletHistoryState.value.filter { it.type } }
            2 -> { _currentHistoryState.value = _walletHistoryState.value.filter { !it.type } }
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
