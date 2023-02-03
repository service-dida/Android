package com.dida.data.model.post

import com.google.gson.annotations.SerializedName

data class PostCommentRequest(
    @SerializedName("postId") val postId: Long,
    @SerializedName("content") val content: String
)
