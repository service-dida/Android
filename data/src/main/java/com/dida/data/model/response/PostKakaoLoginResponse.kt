package com.dida.data.model.response

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostKakaoLoginResponse(
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("accessToken") val accessToken: String?,
    @SerializedName("accessTokenExpirationTime") val accessTokenExpirationTime: String?,
    @SerializedName("refreshToken") val refreshToken: String?,
    @SerializedName("refreshTokenExpirationTime") val refreshTokenExpirationTime: String?,
)
