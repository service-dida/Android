package com.dida.model.domain

data class Comments(
    val comments: List<Comment>
)

data class Comment(
    val commentId: Int,
    val postId: Int,
    val content: String,
    val userName: String,
    val userImgUrl: String,
    val type: String
)
