package com.dida.data.model.main

import com.google.gson.annotations.SerializedName

data class PostLikeRequest(
    @SerializedName("cardId") val cardId: Long
)