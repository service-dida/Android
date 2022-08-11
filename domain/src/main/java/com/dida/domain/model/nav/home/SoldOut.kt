package com.dida.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class SoldOut(
    @SerializedName("nftId") val nftId: Long,
    @SerializedName("nftImg") var nftImg: String,
    @SerializedName("nftName") var nftName: String,
    @SerializedName("userImg") var userImg: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("price") var price: String
){
    fun priceFormat() : String{
        return price
    }
}