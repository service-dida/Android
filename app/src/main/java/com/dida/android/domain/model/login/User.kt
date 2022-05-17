package com.dida.android.domain.model.login

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("jwt") val jwt: String?,
    @SerializedName("message") val message: String
)