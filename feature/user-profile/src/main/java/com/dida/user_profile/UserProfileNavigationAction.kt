package com.dida.user_profile

import com.dida.domain.main.model.Follow
import com.dida.domain.main.model.Nft

sealed class UserProfileNavigationAction {
    class NavigateToDetailNft(val cardId: Long) : UserProfileNavigationAction()
    class NavigateToUserFollowed(val userId: Long, val type: Follow): UserProfileNavigationAction()
    class NavigateToNftUpdate(val nftId: Long): UserProfileNavigationAction()
}
