package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostMarketRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("price") val price: Double
)
