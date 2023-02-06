package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class AssetResponse(
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("uri")
    val uri: String
)
