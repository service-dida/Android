package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetPostsCommentsResponse(
    @SerializedName("commentId") val commentId: Long,
    @SerializedName("content") val content: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("type") val type: String
)
