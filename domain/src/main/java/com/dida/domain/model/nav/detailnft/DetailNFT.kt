package com.dida.domain.model.nav.detailnft

data class DetailNFT(
    val cardId: Long,
    val contracts: String?,
    val description: String,
    val id: String,
    val imgUrl: String,
    val viewerNickname: String,
    val nickname: String,
    val price: String,
    val profileUrl: String,
    val title: String,
    val liked : Boolean,
    val type: String,
    val userId: Long
)
