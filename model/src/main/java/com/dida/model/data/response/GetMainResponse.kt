package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetMainResponse(
    @SerializedName("getHotItems") val getHotItems: List<HotItemResponse>,
    @SerializedName("getHotSellers") val getHotSellers: List<HotSellerResponse>,
    @SerializedName("getRecentCards") val getRecentCards: List<RecentCardResponse>,
    @SerializedName("getHotUsers") val getHotUsers: List<HotUserResponse>
)

data class HotItemResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String,
    @SerializedName("count") val count: String
)

data class HotSellerResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("profileUrl") val profileUrl: String
)

data class RecentCardResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("price") val price: String,
    @SerializedName("liked") val liked: Boolean
)

data class HotUserResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("count") val count: String,
    @SerializedName("followed") val followed: Boolean
)
