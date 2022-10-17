package com.dida.data.model.device

import com.google.gson.annotations.SerializedName

data class PutDeviceTokenRequest(
    @SerializedName("deviceToken") val deviceToken: String
)