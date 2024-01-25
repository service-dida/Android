package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetPostIdCommentsResponse(
    val postIdCommentItems: List<CommentItemResponse>
)

data class GetPostIdPreCommentsResponse(
    val postIdCommentItems: List<CommentItemResponse>
)

data class CommentItemResponse(
    @SerializedName("commentId") val commentId: Long,
    @SerializedName("postId") val postId: Long,
    @SerializedName("content") val content: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("type") val type: String
)
