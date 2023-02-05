package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PutDeviceTokenRequest(
    @SerializedName("deviceToken") val deviceToken: String
)
