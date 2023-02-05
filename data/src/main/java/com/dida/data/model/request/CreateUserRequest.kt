package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("nickname")
    val nickname: String?
)
