package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    object NavigateToBack: DetailCommunityNavigationAction()
    class NavigateToWriterMore(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToNotWriterMore(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToUserProfile(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToCardDetail(val cardId: Long): DetailCommunityNavigationAction()
    class NavigateToPostReport(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToPostBlock(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToUserReport(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToUserBlock(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToUpdate(val commentId: Long): DetailCommunityNavigationAction()
    class NavigateToDelete(val commentId: Long): DetailCommunityNavigationAction()
}
