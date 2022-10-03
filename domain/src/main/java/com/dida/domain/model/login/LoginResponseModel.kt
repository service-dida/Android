package com.dida.domain.model.login

data class LoginResponseModel(
    val accessToken: String?,
    val refreshToken: String?
)