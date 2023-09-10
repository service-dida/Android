package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostKakaoLoginRequest(
    @SerializedName("idToken") val idToken: String
)
