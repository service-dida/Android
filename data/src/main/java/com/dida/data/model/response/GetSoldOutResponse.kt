package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetSoldOutResponse(
    @SerializedName("nftId") val nftId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("price") val price: String,
    @SerializedName("userId") val userId: Long,
    @SerializedName("userName") val userName: String,
    @SerializedName("profileUrl") val profileUrl: String
)
