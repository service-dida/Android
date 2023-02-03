package com.dida.domain.model.nav.post

data class Post(
    val postId: Int,
    val cardId: Int,
    val userName: String,
    val userImgUrl: String,
    val title: String,
    val content: String,
    val cardName: String,
    val cardImgUrl: String,
    val price: String,
    val cardOwnerImgUrl: String,
    val type: String
)
