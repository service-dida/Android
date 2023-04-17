package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    object NavigateToBack: DetailCommunityNavigationAction()
    class NavigateToCommentMore(val commentId: Long): DetailCommunityNavigationAction()
    object NavigateToMyMore: DetailCommunityNavigationAction()
    class NavigateToCommunityMore(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToUpdateCommunity(val postId: Long): DetailCommunityNavigationAction()
    class NavigateToUserProfile(val userId: Long): DetailCommunityNavigationAction()
    class NavigateToCardDetail(val cardId: Long): DetailCommunityNavigationAction()
    object NavigateToDelete: DetailCommunityNavigationAction()
}
