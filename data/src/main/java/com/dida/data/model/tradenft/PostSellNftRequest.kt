package com.dida.data.model.tradenft

import com.google.gson.annotations.SerializedName

data class PostSellNftRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("price") val price : Double
)