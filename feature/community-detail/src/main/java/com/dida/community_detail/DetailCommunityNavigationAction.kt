package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    object NavigateToBack: DetailCommunityNavigationAction()
    class NavigateToCommentMore(val commentId: Long): DetailCommunityNavigationAction()
    object NavigateToCommunityMore: DetailCommunityNavigationAction()
    class NavigateToUpdateCommunity(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToUserProfile(val userId: Int): DetailCommunityNavigationAction()
    class NavigateToCardDetail(val cardId: Long): DetailCommunityNavigationAction()
}
