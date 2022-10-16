package com.dida.android.presentation.views.wallet

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.mypage.WalletCardHolderModel
import com.dida.domain.model.nav.mypage.WalletNFTHistoryHolderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel(), WalletActionHandler {

    private val TAG = "WalletViewModel"

    private val _navigationEvent: MutableSharedFlow<WalletNavigationAction> = MutableSharedFlow<WalletNavigationAction>()
    val navigationEvent: SharedFlow<WalletNavigationAction> = _navigationEvent

    private val _walletHistoryState: MutableStateFlow<List<WalletNFTHistoryHolderModel>> = MutableStateFlow<List<WalletNFTHistoryHolderModel>>(emptyList())
    val walletHistoryState: StateFlow<List<WalletNFTHistoryHolderModel>> = _walletHistoryState

    private val _currentHistoryState: MutableStateFlow<List<WalletNFTHistoryHolderModel>> = MutableStateFlow<List<WalletNFTHistoryHolderModel>>(emptyList())
    val currentHistoryState: StateFlow<List<WalletNFTHistoryHolderModel>> = _currentHistoryState

    /** NFT 거래 내역 타입 LiveData
     * 0 : 전체
     * 1 : 구매
     * 2 : 판매
     */
    private val _nftTypeState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val nftTypeState: StateFlow<Int> = _nftTypeState

    fun setNftHistory(request: List<WalletNFTHistoryHolderModel>) {
        _walletHistoryState.value = request
    }

    override fun onNftHistoryClicked(type: Int) {
        _nftTypeState.value = type
        when(type) {
            0 -> { _currentHistoryState.value = _walletHistoryState.value }
            1 -> { _currentHistoryState.value = _walletHistoryState.value.filter { it.type } }
            2 -> { _currentHistoryState.value = _walletHistoryState.value.filter { it.type } }
        }

    }

    fun onBack() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToBack)
        }
    }
}