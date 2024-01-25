package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostUserWalletRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("pwdCheck") val pwdCheck: String
)
