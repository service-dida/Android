package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostDeviceTokenRequest(
    @SerializedName("deviceToken") val deviceToken: String
)
