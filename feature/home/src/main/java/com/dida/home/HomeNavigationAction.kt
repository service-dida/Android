package com.dida.home

sealed class HomeNavigationAction {
    class NavigateToHotItem(val cardId: Long): HomeNavigationAction()
    class NavigateToHotSeller(val userId: Int) : HomeNavigationAction()
    class NavigateToRecentNftItem(val nftId: Int): HomeNavigationAction()
    class NavigateToSoldOut(val cardId: Long): HomeNavigationAction()
    class NavigateToCollection(val userId: Int) : HomeNavigationAction()
    object NavigateToHotSellerMore : HomeNavigationAction()
    object NavigateToSoldOutMore : HomeNavigationAction()
    object NavigateToRecentNftMore : HomeNavigationAction()
    object NavigateToCollectionMore : HomeNavigationAction()
}
