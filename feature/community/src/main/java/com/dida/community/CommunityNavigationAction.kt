package com.dida.community

sealed class CommunityNavigationAction {
    class NavigateToDetail(val postId: Long): CommunityNavigationAction()
    object NavigateToCommunityWrite: CommunityNavigationAction()
    class NavigateToNftDetail(val cardId: Long): CommunityNavigationAction()
}
