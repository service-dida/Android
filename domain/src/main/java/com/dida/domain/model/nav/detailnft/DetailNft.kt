package com.dida.domain.model.nav.detailnft

data class DetailNft(
    val nftImg: String,
    val nftName: String,
    val nftDetail: String,
    val userImg: String,
    val userName: String,
    val nftLink: String,
    val tokenId: String,
    val community: List<Community>
)