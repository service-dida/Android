package com.dida.data.model.createwallet

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body

data class PostCheckPasswordRequest(
    @SerializedName("pwd")
    val pwd: String
)