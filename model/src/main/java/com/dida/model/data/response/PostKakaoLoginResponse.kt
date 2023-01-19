package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class PostKakaoLoginResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)
