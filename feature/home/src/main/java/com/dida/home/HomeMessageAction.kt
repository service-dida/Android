package com.dida.home

sealed class HomeMessageAction {
    class UserFollowMessage(val nickname: String): HomeMessageAction()
    object UserUnFollowMessage: HomeMessageAction()
}
