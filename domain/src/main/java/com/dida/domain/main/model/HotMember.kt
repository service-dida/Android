package com.dida.domain.main.model

data class HotMember(
    val memberId: Long,
    val memberName: String,
    val profileUrl: String,
    val nftCount: Int,
    val followed: Boolean,
    val me: Boolean
)
