package com.dida.domain.main.model

data class Post(
    val postInfo: PostInfo,
    val memberInfo: MemberInfo,
    val nftInfo: NftInfo,
    val type: String,
    val createdAt: String,
    val comments: List<Comment>
)

data class PostInfo(
    val postId: Long,
    val title: String,
    val content: String
)
