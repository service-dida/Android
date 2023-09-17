package com.dida.domain.main.model

data class HotPost(
    val postId: Long,
    val title: String,
    val commentCnt: Int,
    val nftId: Long,
    val nftImgUrl: String
)

sealed class HotPosts {
    data class Contents(
        val contents: List<HotPost>
    ) : HotPosts()
}
