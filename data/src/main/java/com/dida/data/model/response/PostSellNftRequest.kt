package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class PostSellNftRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("price") val price : Double
)
