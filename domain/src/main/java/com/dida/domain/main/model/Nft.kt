package com.dida.domain.main.model

data class Nft(
    val nftInfo: NftInfo,
    val description: String,
    val memberInfo: MemberInfo,
    val tokenId: String,
    val contractAddress: String,
    val followed: Boolean,
    val liked: Boolean,
    val isMe: Boolean
)
