package com.dida.data.model.post

import com.google.gson.annotations.SerializedName

data class GetPostsCommentsResponse(
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("type") val type: String
)
