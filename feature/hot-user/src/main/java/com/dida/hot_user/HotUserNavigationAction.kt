package com.dida.hot_user

sealed class HotUserNavigationAction {
    class NavigateToUserProfile(val userId: Int): HotUserNavigationAction()
    object NavigateToFollow: HotUserNavigationAction()
}
