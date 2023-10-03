package com.dida.data.model.additional

import com.google.gson.annotations.SerializedName

data class PostMakePictureResponse(
    @SerializedName("url1") val url1: String,
    @SerializedName("url2") val url2: String,
    @SerializedName("url3") val url3: String,
    @SerializedName("url4") val url4: String,
)
