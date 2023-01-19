package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetCardsPostLikeResponse(
    val cardPostLikeItems: List<CardPostLikeItemResponse>
)

data class GetCardsPostMyResponse(
    val cardPostMyItems: List<CardPostLikeItemResponse>
)

data class CardPostLikeItemResponse(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("cardImgUrl") val cardImgUrl: String,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("userName") val userName: String
)
