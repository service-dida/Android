package com.dida.buy.nft.success

sealed class BuySuccessNavigationAction {
    object NavigateToMypage: BuySuccessNavigationAction()
    object NavigateToSaleNft : BuySuccessNavigationAction()
}