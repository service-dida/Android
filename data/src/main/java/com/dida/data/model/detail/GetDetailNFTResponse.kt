package com.dida.data.model.detail

import com.google.gson.annotations.SerializedName

data class GetDetailNFTResponse(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("contracts") val contracts: String?,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("price") val price: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("userId") val userId: Int
)
