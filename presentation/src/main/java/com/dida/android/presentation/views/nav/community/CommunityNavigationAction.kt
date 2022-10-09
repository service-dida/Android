package com.dida.android.presentation.views.nav.community

sealed class CommunityNavigationAction {
    class NavigateToDetail(val communityId: Int): CommunityNavigationAction()
    object NavigateToCommunityWrite: CommunityNavigationAction()
}