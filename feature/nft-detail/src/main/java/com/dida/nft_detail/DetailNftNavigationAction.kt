package com.dida.nft_detail

sealed class DetailNftNavigationAction {
    object NavigateToCommunity: DetailNftNavigationAction()
    class NavigateToItemCommunity(val postId: Long): DetailNftNavigationAction()
    object NavigateToCreateCommunity: DetailNftNavigationAction()
    class NavigateToBuyNft(val nftId: Long) : DetailNftNavigationAction()
    object NavigateToHome : DetailNftNavigationAction()
    class NavigateToUserProfile(val userId: Long): DetailNftNavigationAction()
    object NavigateToBack : DetailNftNavigationAction()
    object NavigateToSell : DetailNftNavigationAction()
    class NavigateToReport(val userId: Long): DetailNftNavigationAction()
    class NavigateToBlock(val userId: Long): DetailNftNavigationAction()
    class NavigateToUpdate(val postId: Long): DetailNftNavigationAction()
    class NavigateToDelete(val postId: Long): DetailNftNavigationAction()
    class NavigateToOwnerShipHistory(val nftId: Long): DetailNftNavigationAction()
    object NavigateToWritePost: DetailNftNavigationAction()
    class NavigateToImageDetail(val imageUrl: String?, val imageTitle: String?, val imageDescription: String?): DetailNftNavigationAction()
}
