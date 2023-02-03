package com.dida.data.model.post

import com.google.gson.annotations.SerializedName

data class GetHotCardsResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("cardImgUrl") val cardImgUrl: String
)
