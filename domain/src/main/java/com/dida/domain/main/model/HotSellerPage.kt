package com.dida.domain.main.model

data class HotSellerPage(
    val memberInfo: HotSellerInfo,
    val nftImgUrl: List<String>
)

data class HotSellerInfo(
    val memberId: Long,
    val memberName: String,
    val profileUrl: String,
    val nftCount: Int,
    val followed: Boolean,
    val me: Boolean
)
