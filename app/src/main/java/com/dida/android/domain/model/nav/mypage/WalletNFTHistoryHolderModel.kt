package com.dida.android.domain.model.nav.mypage

class WalletNFTHistoryHolderModel(
    val image_url: String,
    val user_name: String,
    val nft_name: String,
    val price: Double,
    val type : Boolean
){
    fun priceFormat() : String{
        return price.toString()+" dida"
    }
}