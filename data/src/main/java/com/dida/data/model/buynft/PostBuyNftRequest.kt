package com.dida.data.model.buynft

import com.google.gson.annotations.SerializedName

data class PostBuyNftRequest(
    @SerializedName("buyPwd") val buyPwd: String,
    @SerializedName("marketId") val marketId: Long,
)