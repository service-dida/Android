package com.dida.domain.model.main

data class Comments(
    val commentId: Long,
    val postId: Long,
    val userId: Long,
    val content: String,
    val userName: String,
    val userImgUrl: String,
    val type: String
)
