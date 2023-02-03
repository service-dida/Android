package com.dida.data.model.post

import com.google.gson.annotations.SerializedName

data class PostPostCardIdRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)
