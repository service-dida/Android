package com.dida.domain.model.klaytn

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("uri")
    val uri: String
)