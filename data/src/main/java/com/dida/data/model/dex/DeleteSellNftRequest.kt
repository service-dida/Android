package com.dida.data.model.dex

import com.google.gson.annotations.SerializedName

data class DeleteSellNftRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("marketId") val marketId: Long,
)
