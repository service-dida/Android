package com.dida.domain.model.nav.post

data class Comments(
    val commentId: Int,
    val postId: Int,
    val userId: Long,
    val content: String,
    val userName: String,
    val userImgUrl: String,
    val type: String
)
