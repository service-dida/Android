package com.dida.domain

import com.google.gson.annotations.SerializedName

enum class State {
    SUCCESS, FAIL, NETWORK_ERROR
}

data class BaseResponse(
    val state: State?,
    val data: Any?
)

data class ErrorResponse(
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("status") val status: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("code") val code: Int?,
)

