package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetHotSellerResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("imgUrl") val imgUrl: String
)
