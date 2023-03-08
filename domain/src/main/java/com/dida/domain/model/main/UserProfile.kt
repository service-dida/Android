package com.dida.domain.model.main

data class UserProfile(
    val cardCnt: Int,
    val description: String,
    val followerCnt: Int,
    val followingCnt: Int,
    val getWallet: Boolean,
    val nickname: String,
    val profileUrl: String,
    val userId: Long
)