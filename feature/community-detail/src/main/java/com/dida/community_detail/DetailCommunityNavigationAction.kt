package com.dida.community_detail

sealed class DetailCommunityNavigationAction {
    class NavigateToCommentMore(val commentId: Long): DetailCommunityNavigationAction()
}
