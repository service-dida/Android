package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class SendEmailResponse(
    @SerializedName("random") val random: String
)
