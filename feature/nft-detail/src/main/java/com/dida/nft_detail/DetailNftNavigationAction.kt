package com.dida.nft_detail

sealed class DetailNftNavigationAction {
    object NavigateToCommunity: DetailNftNavigationAction()
    class NavigateToItemCommunity(communityId: Int): DetailNftNavigationAction()
    object NavigateToCreateCommunity: DetailNftNavigationAction()
}
