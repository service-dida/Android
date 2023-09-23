package com.dida.domain.main.model


data class CommonFollow(
    val memberId: Long,
    val nickname: String,
    val profileImgUrl: String?,
    val nftCnt: Int,
    val isFollowing: Boolean
)
