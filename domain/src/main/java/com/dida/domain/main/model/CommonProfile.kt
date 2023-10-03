package com.dida.domain.main.model

data class CommonProfile(
    val memberInfo: MemberInfo,
    val description: String?,
    val nftCnt: Long,
    val followerCnt: Long,
    val followingCnt: Long,
)

data class MemberInfo(
    val memberId: Long,
    val memberName: String,
    val profileImgUrl: String?,
)
