package com.dida.domain.model.nav.mypage

data class OtherUserProfie(
    val userId: Int,
    val nickname: String,
    val profileUrl: String,
    val description: String,
    val cardCnt: Int,
    val followerCnt: Int,
    val followingCnt: Int,
    val followed: Boolean
)
