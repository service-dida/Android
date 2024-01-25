package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetWalletAmountResponse(
    @SerializedName("dida") val dida: Double,
    @SerializedName("klay") val klay: Double
)