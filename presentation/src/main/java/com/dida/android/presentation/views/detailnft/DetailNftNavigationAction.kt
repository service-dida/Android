package com.dida.android.presentation.views.detailnft

sealed class DetailNftNavigationAction {
    object NavigateToCommunity: DetailNftNavigationAction()
    class NavigateToItemCommunity(communityId: Int): DetailNftNavigationAction()
}