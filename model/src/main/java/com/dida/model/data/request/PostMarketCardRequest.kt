package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostMarketCardRequest(
    @SerializedName("buyPwd") val buyPwd: String,
    @SerializedName("marketId") val marketId: Long
)
