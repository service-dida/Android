package com.dida.domain.model.nav.detailnft

import com.google.gson.annotations.SerializedName

class Community(
    @SerializedName("userImg") var userImg: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("clipCheck") var clipCheck: Boolean,
    @SerializedName("contentName") var contentName: String,
    @SerializedName("contentDetail") var contentDetail: String,
    @SerializedName("nftImg") var nftImg: String,
    @SerializedName("nftName") var nftName: String,
    @SerializedName("didaImg") var didaImg: String,
    @SerializedName("didaPrice") var didaPrice: Double,
    @SerializedName("Comments") var Comments: List<Comments>
){
    fun priceFormatter(): String{
        return "$didaPrice dida"
    }
}