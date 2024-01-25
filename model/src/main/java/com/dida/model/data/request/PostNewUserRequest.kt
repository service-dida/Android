package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostNewUserRequest(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String
)
