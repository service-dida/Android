package com.dida.domain.main.model


data class CommonFollow(
    val memberId: Long,
    val memberName: String,
    val profileImgUrl: String?,
    val nftCnt: Int,
)
