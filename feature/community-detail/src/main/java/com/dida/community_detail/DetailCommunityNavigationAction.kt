package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    object NavigateToBack: DetailCommunityNavigationAction()
    class NavigateToCommentMore(val commentId: Long): DetailCommunityNavigationAction()
    class NavigateToWriterMore(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToNotWriterMore(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToUserProfile(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToCardDetail(val cardId: Long): DetailCommunityNavigationAction()
}
