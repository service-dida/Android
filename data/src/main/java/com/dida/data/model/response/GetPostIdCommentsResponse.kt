package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetPostIdCommentsResponse(
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("postId") val postId: Int,
    @SerializedName("userId") val userId: Long,
    @SerializedName("content") val content: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("type") val type: String
)
