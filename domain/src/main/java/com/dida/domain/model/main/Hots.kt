package com.dida.domain.model.main

data class Hots(
    val cardId: Long,
    val nftImg: String,
    val nftName: String,
    val heartCount: String,
    val price: String
){
    fun heartFormat(): String{
        return heartCount
    }
}
