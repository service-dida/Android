package com.dida.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponseImpl(
    @SerializedName("code") override val code: String?,
    @SerializedName("message") override val message: String?,
    @SerializedName("timestamp") override val timestamp: String?,
) : ErrorResponse


