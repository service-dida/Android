package com.dida.recent_nft

sealed class RecentNftNavigationAction {
    class NavigateToRecentNftItem(val nftId: Long): RecentNftNavigationAction()
    object NavigateToCardRefresh: RecentNftNavigationAction()
}
