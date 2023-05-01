package com.dida.wallet

sealed class WalletNavigationAction {
    object NavigateToBack: WalletNavigationAction()
    object NavigateToSwapHistory : WalletNavigationAction()
    object NavigateToHotCard: WalletNavigationAction()
}
