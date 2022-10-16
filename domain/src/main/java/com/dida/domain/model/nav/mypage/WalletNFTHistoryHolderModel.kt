package com.dida.domain.model.nav.mypage

data class WalletNFTHistoryHolderModel(
    val historyId: Int,
    val image_url: String,
    val user_name: String,
    val nft_name: String,
    val price: Double,
    val type : Boolean
){
    fun priceFormat() : String{
        return "$price dida"
    }
}