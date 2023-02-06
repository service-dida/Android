package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetMainResponse(
    @SerializedName("getHotItems") val getHotItems: List<HotItemResponse>,
    @SerializedName("getHotSellers") val getHotSellers: List<HotSellerResponse>,
    @SerializedName("getRecentCards") val getRecentCards: List<RecentCardResponse>,
    @SerializedName("getHotUsers") val getHotUsers: List<HotUserResponse>
)
//{
//    "getHotItems": [],
//    "getHotSellers": [],
//    "getRecentCards": [],
//    "getHotUsers": []
//}
