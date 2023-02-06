package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class RecentCardResponse(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("price") val price: String,
    @SerializedName("liked") val liked: Boolean
)
