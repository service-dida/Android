package com.dida.domain.main.model

data class Comment(
    val commentInfo: CommentInfo,
    val memberInfo: MemberInfo,
    val type: String
)

data class CommentInfo(
    val commentId: Long,
    val content: String
)
