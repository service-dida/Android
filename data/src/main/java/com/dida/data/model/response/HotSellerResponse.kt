package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class HotSellerResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("profileUrl") val profileUrl: String
)
