package com.dida.buy.nft

sealed class BuyNftNavigationAction {
    class NavigateToSuccess(val cardId: Long): BuyNftNavigationAction()
    object NavigateToFailAlert: BuyNftNavigationAction()
}
