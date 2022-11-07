package com.dida.data.model.tradenft

import com.google.gson.annotations.SerializedName

data class PostBuyNftRequest(
    @SerializedName("buyPwd") val buyPwd: String,
    @SerializedName("marketId") val marketId: Long,
)