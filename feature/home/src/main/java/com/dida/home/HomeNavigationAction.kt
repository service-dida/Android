package com.dida.home

sealed class HomeNavigationAction {
    class NavigateToHotItem(val cardId: Long): HomeNavigationAction()
    class NavigateToHotSeller(val userId: Long) : HomeNavigationAction()
    class NavigateToRecentNftItem(val nftId: Long): HomeNavigationAction()
    class NavigateToSoldOut(val cardId: Long): HomeNavigationAction()
    class NavigateToCollection(val userId: Long) : HomeNavigationAction()
    object NavigateToHotSellerMore : HomeNavigationAction()
    object NavigateToSoldOutMore : HomeNavigationAction()
    object NavigateToRecentNftMore : HomeNavigationAction()
    object NavigateToCollectionMore : HomeNavigationAction()
}
