package com.dida.domain.main.model

data class HotItem(
    val nftId: Long,
    val nftImgUrl: String,
    val nftName: String,
    val price: String,
    val likeCount: String
)

sealed class HotItems {
    data class Contents(
        val contents: List<HotItem>
    ) : HotItems()
}
