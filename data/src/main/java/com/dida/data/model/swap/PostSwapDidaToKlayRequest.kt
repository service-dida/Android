package com.dida.data.model.swap

import com.google.gson.annotations.SerializedName

data class PostSwapDidaToKlayRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("dida") val dida: Double,
)