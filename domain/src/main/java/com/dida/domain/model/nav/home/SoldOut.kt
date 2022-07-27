package com.dida.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class SoldOut(
    @SerializedName("nftImg") var nftImg: String,
    @SerializedName("nftName") var nftName: String,
    @SerializedName("userImg") var userImg: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("price") var price: Double
){
    fun priceFormat() : String{
        return price.toString()
    }
}