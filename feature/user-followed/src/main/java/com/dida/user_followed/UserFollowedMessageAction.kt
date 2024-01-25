package com.dida.user_followed

sealed class UserFollowedMessageAction {
    class UserFollowMessage(val nickname: String): UserFollowedMessageAction()
    object UserUnFollowMessage: UserFollowedMessageAction()
}
