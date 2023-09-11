package com.dida.data.model.sns

import com.google.gson.annotations.SerializedName

data class PostCommonPostRequest(
    @SerializedName("nftId") val nftId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
)
