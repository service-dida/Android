package com.dida.hot_user

interface HotUserActionHandler {
    fun onUserClicked(userId: Int)
    fun onUserFollowed(userId: Int)
}
