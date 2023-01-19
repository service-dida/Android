package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostKakaoLogineRequest(
    @SerializedName("idToken") val idToken: String
)
