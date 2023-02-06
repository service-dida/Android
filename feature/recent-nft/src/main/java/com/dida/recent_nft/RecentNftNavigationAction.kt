package com.dida.recent_nft

sealed class RecentNftNavigationAction {
    class NavigateToRecentNftItem(val nftId: Int): RecentNftNavigationAction()
    object NavigateToCardRefresh: RecentNftNavigationAction()
}
