package com.dida.user_profile

sealed class UserMessageAction {
    class UserFollowMessage(val nickname: String): UserMessageAction()
    object UserUnFollowMessage: UserMessageAction()
    class NftBookmarkMessage(val liked: Boolean): UserMessageAction()
}
