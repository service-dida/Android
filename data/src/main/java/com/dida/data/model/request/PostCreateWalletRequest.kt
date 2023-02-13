package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostCreateWalletRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("pwdCheck") val pwdCheck: String
)
