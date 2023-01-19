package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetMainTermResponse(
    val mainTermItems: List<MainTermItemResponse>
)

data class MainTermItemResponse(
    @SerializedName("nftId") val nftId: Long,
    @SerializedName("nftImg") val nftImg: String,
    @SerializedName("nftName") val nftName: String,
    @SerializedName("userImg") val userImg: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("price") val price: String
) {
    fun priceFormat() : String = price
}
