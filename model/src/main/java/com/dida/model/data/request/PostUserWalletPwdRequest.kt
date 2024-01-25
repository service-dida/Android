package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostUserWalletPwdRequest(
    @SerializedName("nowPwd") val nowPwd: String,
    @SerializedName("changePwd") val changePwd: String
)
