package com.dida.model.domain

data class MainTerm(
    val mainTermItems: List<MainTermItem>
)

data class MainTermItem(
    val nftId: Long,
    val nftImg: String,
    val nftName: String,
    val userImg: String,
    val userName: String,
    val price: String
) {
    fun priceFormat() : String = price
}
