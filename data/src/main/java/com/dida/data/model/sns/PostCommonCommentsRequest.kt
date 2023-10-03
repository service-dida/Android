package com.dida.data.model.sns

import com.google.gson.annotations.SerializedName

data class PostCommonCommentsRequest(
    @SerializedName("postId") val postId: Long,
    @SerializedName("content") val content: String,
)
