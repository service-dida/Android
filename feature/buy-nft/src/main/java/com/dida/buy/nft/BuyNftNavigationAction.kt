package com.dida.buy.nft

sealed class BuyNftNavigationAction {
    object NavigateToSuccess: BuyNftNavigationAction()
    object NavigateToFailAlert: BuyNftNavigationAction()
}