package com.dida.data.model.response.login

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("accessToken") val accessToken: String?,
    @SerializedName("accessTokenExpirationTime") val accessTokenExpirationTime: String?,
    @SerializedName("refreshToken") val refreshToken: String?,
    @SerializedName("refreshTokenExpirationTime") val refreshTokenExpirationTime: String?,
)
