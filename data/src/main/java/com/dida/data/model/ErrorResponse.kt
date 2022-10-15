package com.dida.data.model

interface ErrorResponse {
    val timestamp: String?
    val status: Int?
    val message: String?
    val code: Int?
}