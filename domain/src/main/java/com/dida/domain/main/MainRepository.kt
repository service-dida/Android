package com.dida.domain.main

import com.dida.domain.NetworkResult
import com.dida.domain.main.model.LoginToken

interface  MainRepository {

    suspend fun login(idToken: String): NetworkResult<LoginToken>
    suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken>
    suspend fun checkNickname(nickname: String): NetworkResult<Boolean>
    suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken>
}
