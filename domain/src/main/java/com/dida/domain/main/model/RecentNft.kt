package com.dida.domain.main.model

data class RecentNft(
    val nftId: Long,
    val nftName: String,
    val memberName: String,
    val nftImgUrl: String,
    val price: String,
    val liked: Boolean,
)
