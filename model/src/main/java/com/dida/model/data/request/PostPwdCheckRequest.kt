package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostPwdCheckRequest(
    @SerializedName("pwd") val pwd: String
)
