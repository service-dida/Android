package com.dida.data.model.createwallet

import com.google.gson.annotations.SerializedName

data class SendEmailResponse(
    @SerializedName("random") val random: String
)