package com.dida.model.domain

data class User(
    val userId: Int,
    val nickname: String,
    val profileUrl: String,
    val description: String,
    val getWallet: Boolean,
    val cardCnt: Int,
    val followerCnt: Int,
    val followingCnt: Int
)
