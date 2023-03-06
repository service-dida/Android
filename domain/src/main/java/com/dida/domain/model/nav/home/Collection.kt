package com.dida.domain.model.nav.home

data class Collection(
    val userId: Long,
    val userImg: String,
    val userName: String,
    val userDetail: String,
    val follow: Boolean
)