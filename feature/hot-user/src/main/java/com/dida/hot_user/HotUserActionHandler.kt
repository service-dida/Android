package com.dida.hot_user

interface HotUserActionHandler {
    fun onUserClicked(userId: Long)
    fun onUserFollowed(userId: Long)
}
