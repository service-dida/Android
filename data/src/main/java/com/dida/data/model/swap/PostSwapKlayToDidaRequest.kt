package com.dida.data.model.swap

import com.google.gson.annotations.SerializedName

data class PostSwapKlayToDidaRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("klay") val klay: Double,
)