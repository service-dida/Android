package com.dida.data.model.dex

import com.google.gson.annotations.SerializedName

data class PostNftRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
)
