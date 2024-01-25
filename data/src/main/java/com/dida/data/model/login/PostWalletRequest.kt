package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PostWalletRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("checkPwd") val checkPwd: String,
)
