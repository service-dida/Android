package com.dida.notification

sealed class NotificationNavigationAction {
    class NavigateToPost(val postId: Long): NotificationNavigationAction()
    object NavigateToUserFollowed: NotificationNavigationAction()
    class NavigateToNft(val nftId: Long): NotificationNavigationAction()
}
