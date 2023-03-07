package com.dida.wallet

interface WalletActionHandler {
    fun onHistoryTypeClicked(type: Int)
    fun onSwapHistoryClicked()

    //TODO : 이벤트 클릭상황이 생기면 ID값 넣어줘야함
    fun onNftItemClicked()
}
