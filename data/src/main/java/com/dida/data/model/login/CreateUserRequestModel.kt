package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class CreateUserRequestModel(
    @SerializedName("email")
    val email: String?,
    @SerializedName("nickname")
    val nickname: String?
)