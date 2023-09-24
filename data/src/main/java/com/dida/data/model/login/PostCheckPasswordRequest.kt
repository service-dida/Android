package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PostCheckPasswordRequest(
    @SerializedName("payPwd") val payPwd: String
)
