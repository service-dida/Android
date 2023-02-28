package com.dida.model.domain

data class Main(
    val getHotItems: List<HotItem>,
    val getHotSellers: List<HotSeller>,
    val getRecentCards: List<RecentCard>,
    val getHotUsers: List<HotUser>
)

data class HotItem(
    val cardId: Int,
    val nftImg: String,
    val nftName: String,
    val heartCount: String,
    val price: String
) {
    fun heartFormat(): String = heartCount+"K"
}

data class HotSeller(
    val userId: Int,
    val sellerBackground: String,
    val sellerProfile: String,
    val sellerName: String
)

data class RecentCard(
    val cardId : Int,
    val userName : String,
    val cardName : String,
    val imgUrl : String,
    val price : String,
    val liked: Boolean
)

data class HotUser(
    val userId: Int,
    val userImg: String,
    val userName: String,
    val userDetail: String,
    val follow: Boolean
)
