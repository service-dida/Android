package com.dida.domain.model.main

data class Collection(
    val userId: Long,
    val userImg: String,
    val userName: String,
    val userDetail: String,
    val follow: Boolean
)