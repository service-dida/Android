package com.dida.domain.model.nav.post

data class Posts(
    val postId: Long,
    val cardId: Long,
    val userName: String,
    val userImgUrl: String,
    val title: String,
    val content: String,
    val cardName: String,
    val cardImgUrl: String,
    val price: String,
    val cardOwnerImgUrl: String,
    val commentList: List<PostComments>
)
