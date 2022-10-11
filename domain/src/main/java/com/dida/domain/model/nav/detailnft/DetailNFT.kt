package com.dida.domain.model.nav.detailnft

data class DetailNFT(
    val cardId: Int,
    val contracts: String?,
    val description: String,
    val id: String,
    val imgUrl: String,
    val nickname: String,
    val price: String,
    val profileUrl: String,
    val title: String,
    val type: String,
    val userId: Int
)