package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostSwapDidaToKlayRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("dida") val dida: Double,
)
