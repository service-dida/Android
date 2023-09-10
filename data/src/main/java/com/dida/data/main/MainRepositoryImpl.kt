package com.dida.data.main

import com.dida.data.api.handleApi
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.LoginToken
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainRemoteService: MainRemoteService
): MainRepository {

    override suspend fun login(idToken: String): NetworkResult<LoginToken> {
        val body = PostLoginRequest(idToken = idToken)
        return handleApi { mainRemoteService.login(body).toDomain() }
    }
}

