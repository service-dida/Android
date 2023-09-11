package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class GetEmailAuthResponse(
    @SerializedName("random") val random: String
)
