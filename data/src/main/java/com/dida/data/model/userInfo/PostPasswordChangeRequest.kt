package com.dida.data.model.userInfo

import com.google.gson.annotations.SerializedName

data class PostPasswordChangeRequest(
    @SerializedName("nowPwd") val nowPwd: String,
    @SerializedName("changePwd") val changePwd: String
)