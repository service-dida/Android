package com.dida.domain.model.nav.home

data class HotUser(
    val userId: Int,
    val name: String,
    val profileUrl: String,
    val cardCnt: String,
    val cardUrls: List<String>,
    val isFollowed: Boolean
)
