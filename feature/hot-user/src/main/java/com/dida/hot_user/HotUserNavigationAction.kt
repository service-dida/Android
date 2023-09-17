package com.dida.hot_user

sealed class HotUserNavigationAction {
    class NavigateToUserProfile(val userId: Long): HotUserNavigationAction()
}
