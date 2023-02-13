package com.dida.domain.model.nav.home

data class Hots(
    val cardId: Int,
    val nftImg: String,
    val nftName: String,
    val heartCount: String,
    val price: String
){
    fun priceFormat() : String {
        return if(price.isBlank()) "판매X" else "$price dida"
    }

    fun heartFormat(): String{
        return heartCount
    }
}
