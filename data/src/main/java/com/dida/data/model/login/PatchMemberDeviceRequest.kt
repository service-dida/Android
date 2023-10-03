package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PatchMemberDeviceRequest(
    @SerializedName("deviceToken") val deviceToken: String,
)
