package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    object NavigateToBack: DetailCommunityNavigationAction()
    class NavigateToWriterMore(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToNotWriterMore(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToUserProfile(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToCardDetail(val cardId: Long): DetailCommunityNavigationAction()
    class NavigateToReport(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToBlock(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToUpdate(val commentId: Long): DetailCommunityNavigationAction()
    class NavigateToDelete(val commentId: Long): DetailCommunityNavigationAction()
}
