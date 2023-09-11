package com.dida.data.model.sns

import com.google.gson.annotations.SerializedName

data class PatchCommonPostRequest(
    @SerializedName("postId") val postId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
)
