package com.dida.domain.model.main

data class PostComments(
    val commentId: Long,
    val content: String,
    val userName: String,
    val userImgUrl: String,
    val type: String
)
