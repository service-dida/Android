package com.dida.user_profile

import com.dida.domain.main.model.Follow

sealed class UserProfileNavigationAction {
    class NavigateToDetailNft(val cardId: Long) : UserProfileNavigationAction()
    class NavigateToUserFollowed(val userId: Long, val type: Follow): UserProfileNavigationAction()
}
