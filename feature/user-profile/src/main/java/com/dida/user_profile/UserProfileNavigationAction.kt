package com.dida.user_profile

sealed class UserProfileNavigationAction {
    class NavigateToDetailNft(val cardId: Long) : UserProfileNavigationAction()
    object NavigateToCardLikeButtonClicked: UserProfileNavigationAction()
    class NavigateToUserFollowed(val userId: Long): UserProfileNavigationAction()
}
