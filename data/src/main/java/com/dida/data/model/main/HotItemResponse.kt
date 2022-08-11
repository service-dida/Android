package com.dida.data.model.main

import com.google.gson.annotations.SerializedName

data class HotItemResponse(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String,
    @SerializedName("count") val count: String
)