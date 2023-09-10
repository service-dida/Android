package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("idToken") val idToken: String
)
