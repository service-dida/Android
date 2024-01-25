package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetCardResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("userId") val userId: Long,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("id") val id: String,
    @SerializedName("contracts") val contracts: String?,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: String
)
