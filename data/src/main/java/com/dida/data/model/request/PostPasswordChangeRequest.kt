package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostPasswordChangeRequest(
    @SerializedName("nowPwd") val nowPwd: String,
    @SerializedName("checkPwd") val checkPwd: String
)
