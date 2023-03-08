package com.dida.domain.model.main

class Community(
    val userImg: String,
    val userName: String,
    val clipCheck: Boolean,
    val contentName: String,
    val contentDetail: String,
    val nftImg: String,
    val nftName: String,
    val didaImg: String,
    val didaPrice: Double,
    val Comments: List<Comments>
)
