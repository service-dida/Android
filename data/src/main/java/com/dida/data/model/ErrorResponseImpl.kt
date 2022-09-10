package com.dida.data.model

data class ErrorResponseImpl(
    override val timestamp: String?,
    override val status: Int?,
    override val message: String?,
    override val code: Int?
) : ErrorResponse

