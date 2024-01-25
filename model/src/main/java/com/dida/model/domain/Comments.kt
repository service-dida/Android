package com.dida.model.domain

data class Comments(
    val comments: List<Comment>
)

data class Comment(
    val commentId: Long,
    val postId: Long,
    val content: String,
    val userName: String,
    val userImgUrl: String,
    val type: String
)
