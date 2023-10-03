package com.dida.data.model

interface ErrorResponse {
    val code: String?
    val message: String?
    val timestamp: String?
}
