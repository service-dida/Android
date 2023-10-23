package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class PostMemberPasswordCheckRequest(
    @SerializedName("payPwd") val payPwd: String,
)
