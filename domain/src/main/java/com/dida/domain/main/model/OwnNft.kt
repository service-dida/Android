package com.dida.domain.main.model

data class OwnNft(
    val nftId: Long,
    val nftName: String,
    val nftImgUrl: String,
    val memberInfo: MemberInfo
)
