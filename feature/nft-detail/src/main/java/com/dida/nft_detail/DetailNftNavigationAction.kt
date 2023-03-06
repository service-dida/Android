package com.dida.nft_detail

sealed class DetailNftNavigationAction {
    object NavigateToCommunity: DetailNftNavigationAction()
    class NavigateToItemCommunity(val postId: Long): DetailNftNavigationAction()
    object NavigateToCreateCommunity: DetailNftNavigationAction()
    object NavigateToHome : DetailNftNavigationAction()
    class NavigateToUserProfile(val userId: Int): DetailNftNavigationAction()
    object NavigateToBack : DetailNftNavigationAction()
    object NavigateToSell : DetailNftNavigationAction()
}
