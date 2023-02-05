package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    object NavigateToBack: DetailCommunityNavigationAction()
    class NavigateToCommentMore(val commentId: Long): DetailCommunityNavigationAction()
    object NavigateToCommunityMore: DetailCommunityNavigationAction()
}
