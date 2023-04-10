package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetHotCardsResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("cardImgUrl") val cardImgUrl: String,
    @SerializedName("postId") val postId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("comments") val comments: Long
)
