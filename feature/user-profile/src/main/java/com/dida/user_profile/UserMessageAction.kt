package com.dida.user_profile

sealed class UserMessageAction {
    class UserFollowMessage(val nickname: String): UserMessageAction()
    object UserUnFollowMessage: UserMessageAction()
    object AddCardBookmarkMessage: UserMessageAction()
    object DeleteCardBookmarkMessage: UserMessageAction()
}
