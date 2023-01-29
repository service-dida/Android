package com.dida.data.model.post

import com.google.gson.annotations.SerializedName

data class GetCardsPostResponse(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("cardImgUrl") val cardImgUrl: String,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("userName") val userName: String
)
