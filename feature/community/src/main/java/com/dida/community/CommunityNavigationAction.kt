package com.dida.community

sealed class CommunityNavigationAction {
    class NavigateToDetail(val postId: Long): CommunityNavigationAction()
    object NavigateToCommunityWrite: CommunityNavigationAction()
    class NavigateToNftDetail(val cardId: Long): CommunityNavigationAction()
    class NavigateToReport(val postId: Long): CommunityNavigationAction()
    class NavigateToBlock(val postId: Long): CommunityNavigationAction()
    object NavigateToRefresh: CommunityNavigationAction()
    class NavigateToUpdate(val postId: Long): CommunityNavigationAction()
    class NavigateToDelete(val postId: Long): CommunityNavigationAction()
}
