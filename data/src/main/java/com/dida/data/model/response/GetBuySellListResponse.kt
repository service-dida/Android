package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetBuySellListResponse(
    @SerializedName("cardName") val cardName: String,
    @SerializedName("cardImgUrl") val cardImgUrl: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("price") val price: Float,
    @SerializedName("type") val type: String,
)
