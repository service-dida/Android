package com.dida.android.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class Hots(
    @SerializedName("nftImg") var nftImg: String,
    @SerializedName("nftName") var nftName: String,
    @SerializedName("heartCount") var heartCount: Double,
    @SerializedName("price") var price: Double
){
    fun priceFormat() : String{
        return price.toString()+" dida"
    }

    fun heartFormat(): String{
        return heartCount.toString()+"K"
    }
}