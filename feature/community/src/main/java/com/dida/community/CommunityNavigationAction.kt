package com.dida.community

sealed class CommunityNavigationAction {
    class NavigateToDetail(val postId: Long): CommunityNavigationAction()
    object NavigateToCommunityWrite: CommunityNavigationAction()
    class NavigateToNftDetail(val cardId: Long): CommunityNavigationAction()
    class NavigateToReport(val userId: Long): CommunityNavigationAction()
    class NavigateToBlock(val userId: Long): CommunityNavigationAction()
    class NavigateToUpdate(val postId: Long): CommunityNavigationAction()
    class NavigateToDelete(val postId: Long): CommunityNavigationAction()
}
