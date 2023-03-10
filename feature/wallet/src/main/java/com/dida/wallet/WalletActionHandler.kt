package com.dida.wallet

interface WalletActionHandler {
    fun onHistoryTypeClicked(type: Int)
    fun onSwapHistoryClicked()

    fun onNftItemClicked(cardId: Long)
}
