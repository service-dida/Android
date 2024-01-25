package com.dida.data.model.dex

import com.google.gson.annotations.SerializedName

data class PostBuyNftRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("marketId") val marketId: Long,
)
