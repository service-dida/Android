package com.dida.hot_seller

sealed class HotSellerMessageAction {
    class UserFollowMessage(val nickname: String): HotSellerMessageAction()
    object UserUnFollowMessage: HotSellerMessageAction()
}
