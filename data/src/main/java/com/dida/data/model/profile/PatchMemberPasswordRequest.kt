package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class PatchMemberPasswordRequest(
    @SerializedName("nowPwd") val nowPwd: String,
    @SerializedName("changePwd") val changePwd: String,
)
