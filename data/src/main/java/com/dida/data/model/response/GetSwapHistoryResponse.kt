package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetSwapHistoryResponse(
    @SerializedName("sendCoinType") val sendCoinType: String,
    @SerializedName("receiveCoinType") val receiveCoinType: String,
    @SerializedName("sendAmount") val sendAmount: Double,
    @SerializedName("time") val time: String
)
