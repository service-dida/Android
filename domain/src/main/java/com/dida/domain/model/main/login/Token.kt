package com.dida.domain.model.main.login

data class Token(
    val message: String?,
    val accessToken: String?,
    val refreshToken: String?
)
