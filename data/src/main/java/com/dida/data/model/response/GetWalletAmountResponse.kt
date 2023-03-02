package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetWalletAmountResponse(
    @SerializedName("walletId") val walletId: Long,
    @SerializedName("address") val address: String,
    @SerializedName("dida") val dida: Double,
    @SerializedName("klay") val klay: Double

)
