package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostCardLikeRequest(
    @SerializedName("cardId") val cardId: Long
)
