package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class GetMemberWalletResponse(
    @SerializedName("address") val address: String,
    @SerializedName("klay") val klay: Float,
    @SerializedName("dida") val dida: Float,
)
