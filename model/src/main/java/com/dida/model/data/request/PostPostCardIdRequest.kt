package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostPostCardIdRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)
