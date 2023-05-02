package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetPostsResponse(
    @SerializedName("postId") val postId: Long,
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImgUrl") val userImgUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("cardImgUrl") val cardImgUrl: String,
    @SerializedName("price") val price: String,
    @SerializedName("cardOwnerImgUrl") val cardOwnerImgUrl: String,
    @SerializedName("commentsList") val commentsList: List<GetPostsCommentsResponse>,
    @SerializedName("type") val type: String
)
