package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostCardRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String
)
