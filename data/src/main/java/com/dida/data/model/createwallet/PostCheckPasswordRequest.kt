package com.dida.data.model.createwallet

import com.google.gson.annotations.SerializedName

data class PostCheckPasswordRequest(
    @SerializedName("pwd") val pwd: String
)