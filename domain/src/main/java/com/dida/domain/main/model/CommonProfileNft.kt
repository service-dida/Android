package com.dida.domain.main.model


data class CommonProfileNft(
    val nftInfo: NftInfo,
    val liked: Boolean,
)

data class NftInfo(
    val nftId: Long,
    val nftName: String,
    val nftImgUrl: String,
    val price: String,
)
