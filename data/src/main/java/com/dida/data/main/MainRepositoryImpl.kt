package com.dida.data.main

import com.dida.data.api.handleApi
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostUserRequest
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

    override suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken> {
        val body = PostUserRequest(email = email, nickname = nickname)
        return handleApi { mainRemoteService.postUser(body).toDomain() }
    }

    override suspend fun checkNickname(nickname: String): NetworkResult<Boolean> {
        val body = PostNicknameRequest(nickname = nickname)
        return handleApi { mainRemoteService.postNickname(body).used }
    }

    override suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken> {
        return handleApi { mainRemoteService.patchRefreshToken(request = refreshToken).toDomain() }
    }

    override suspend fun emailAuth(): NetworkResult<String> {
        return handleApi { mainRemoteService.getEmailAuth().random }
    }

    override suspend fun checkWallet(): NetworkResult<Boolean> {
        return handleApi { mainRemoteService.getCommonWallet().existed }
    }

    override suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit> {
        val body = PatchMemberDeviceRequest(deviceToken = deviceToken)
        return handleApi { mainRemoteService.patchMemberDevice(body) }
    }
}

