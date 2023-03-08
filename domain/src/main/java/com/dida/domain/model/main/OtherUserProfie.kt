package com.dida.domain.model.main

data class OtherUserProfie(
    val userId: Long,
    val nickname: String,
    val profileUrl: String,
    val description: String,
    val cardCnt: Int,
    val followerCnt: Int,
    val followingCnt: Int,
    val followed: Boolean
)
