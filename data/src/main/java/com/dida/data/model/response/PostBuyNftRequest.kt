package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class PostBuyNftRequest(
    @SerializedName("buyPwd") val buyPwd: String,
    @SerializedName("marketId") val marketId: Long,
)
