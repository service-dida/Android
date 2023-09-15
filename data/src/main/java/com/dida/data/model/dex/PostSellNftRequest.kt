package com.dida.data.model.dex

import com.google.gson.annotations.SerializedName

data class PostSellNftRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("nftId") val nftId: Long,
    @SerializedName("price") val price: Double,
)
