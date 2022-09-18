package com.dida.data.model.klaytn

import com.google.gson.annotations.SerializedName

data class MetaDataResponse(
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("uri")
    val uri: String
)