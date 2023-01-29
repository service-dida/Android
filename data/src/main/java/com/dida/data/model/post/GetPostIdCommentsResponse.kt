package com.dida.data.model.post

import com.google.gson.annotations.SerializedName

data class GetPostIdCommentsResponse(
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("postId") val postId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("type") val type: String
)
