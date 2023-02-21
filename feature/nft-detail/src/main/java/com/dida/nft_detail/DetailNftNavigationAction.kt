package com.dida.nft_detail

sealed class DetailNftNavigationAction {
    object NavigateToCommunity: DetailNftNavigationAction()
    class NavigateToItemCommunity(val postId: Long): DetailNftNavigationAction()
    object NavigateToCreateCommunity: DetailNftNavigationAction()
    class NavigateToBuyNft(val nftId : Long, val nftImg : String, val nftTitle : String, val userImg : String,
                           val userName : String, val price : String ,val viewerNickName : String) : DetailNftNavigationAction()
    object NavigateToHome : DetailNftNavigationAction()
    class NavigateToUserProfile(val userId: Long): DetailNftNavigationAction()
    object NavigateToBack : DetailNftNavigationAction()
    object NavigateToSell : DetailNftNavigationAction()
}
