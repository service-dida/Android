package com.dida.data.model.dex

import com.google.gson.annotations.SerializedName

data class PostSwapRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("coin") val coin: Int
)
