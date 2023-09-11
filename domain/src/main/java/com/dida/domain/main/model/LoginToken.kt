package com.dida.domain.main.model

data class LoginToken(
    val message: String?,
    val accessToken: String?,
    val refreshToken: String?,
)
