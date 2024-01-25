package com.dida.model.domain

data class Posts(
    val postItemItems: List<PostItem>
)

data class PostItem(
    val postId: Long,
    val cardId: Long,
    val userName: String,
    val userImgUrl: String,
    val title: String,
    val content: String,
    val cardName: String,
    val cardImgUrl: String,
    val price: String?,
    val cardOwnerImgUrl: String
)
