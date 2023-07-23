package com.dida.user_profile

import com.dida.domain.model.main.Follow

sealed class UserProfileNavigationAction {
    class NavigateToDetailNft(val cardId: Long) : UserProfileNavigationAction()
    object NavigateToCardLikeButtonClicked: UserProfileNavigationAction()
    class NavigateToUserFollowed(val userId: Long, val type: Follow): UserProfileNavigationAction()
}
