package com.dida.domain.model.main

data class Post(
    val postId: Long,
    val cardId: Long,
    val userId: Long,
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
