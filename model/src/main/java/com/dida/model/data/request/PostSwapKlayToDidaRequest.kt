package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostSwapKlayToDidaRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("klay") val klay: Double,
)