package com.dida.domain.model.nav.mypage

data class BuySellList(
    val cardName: String,
    val cardImgUrl: String,
    val userName: String,
    val price: Float,
    val type: String
) {
    fun priceFormat() : String{
        return "$price dida"
    }
}
