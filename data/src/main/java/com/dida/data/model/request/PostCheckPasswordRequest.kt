package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostCheckPasswordRequest(
    @SerializedName("pwd") val pwd: String
)
