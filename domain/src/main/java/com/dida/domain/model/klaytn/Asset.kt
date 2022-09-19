package com.dida.domain.model.klaytn

import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("uri")
    val uri: String
)