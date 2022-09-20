package com.dida.android.presentation.views.wallet

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
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

    /** NFT 거래 내역 타입 LiveData
     * 0 : 전체
     * 1 : 구매
     * 2 : 판매
     */
    private val _nftTypeState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val nftTypeState: StateFlow<Int> = _nftTypeState

    override fun onNftHistoryClicked(type: Int) {
        _nftTypeState.value = type
    }

    fun onBack() {
        baseViewModelScope.launch {
            _navigationEvent.emit(WalletNavigationAction.NavigateToBack)
        }
    }
}