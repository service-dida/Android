package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostNftMintRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String
)
