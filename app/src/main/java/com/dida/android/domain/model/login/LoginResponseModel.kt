package com.dida.android.domain.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?
)