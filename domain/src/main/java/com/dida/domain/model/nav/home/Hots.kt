package com.dida.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class Hots(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("nftImg") var nftImg: String,
    @SerializedName("nftName") var nftName: String,
    @SerializedName("heartCount") var heartCount: String,
    @SerializedName("price") var price: String
){
    fun priceFormat() : String{
        return price+" dida"
    }

    fun heartFormat(): String{
        return heartCount+"K"
    }
}