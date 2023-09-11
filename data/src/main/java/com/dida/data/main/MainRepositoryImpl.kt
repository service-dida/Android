package com.dida.data.main

import com.dida.data.api.handleApi
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostUserRequest
import com.dida.data.model.login.toDomain
import com.dida.data.model.profile.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.Contents
import com.dida.domain.main.model.LoginToken
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val didaApi: DidaApi
): MainRepository {

    override suspend fun login(idToken: String): NetworkResult<LoginToken> {
        val body = PostLoginRequest(idToken = idToken)
        return handleApi { didaApi.login(body).toDomain() }
    }

    override suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken> {
        val body = PostUserRequest(email = email, nickname = nickname)
        return handleApi { didaApi.postUser(body).toDomain() }
    }

    override suspend fun checkNickname(nickname: String): NetworkResult<Boolean> {
        val body = PostNicknameRequest(nickname = nickname)
        return handleApi { didaApi.postNickname(body).used }
    }

    override suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken> {
        return handleApi { didaApi.patchRefreshToken(request = refreshToken).toDomain() }
    }

    override suspend fun emailAuth(): NetworkResult<String> {
        return handleApi { didaApi.getEmailAuth().random }
    }

    override suspend fun checkWallet(): NetworkResult<Boolean> {
        return handleApi { didaApi.getCommonWallet().existed }
    }

    override suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit> {
        val body = PatchMemberDeviceRequest(deviceToken = deviceToken)
        return handleApi { didaApi.patchMemberDevice(body) }
    }

    override suspend fun deleteUser(): NetworkResult<Unit> {
        return handleApi { didaApi.deleteUser() }
    }

    override suspend fun commonProfile(): NetworkResult<CommonProfile> {
        return handleApi { didaApi.getCommonProfile().toDomain() }
    }

    override suspend fun commonProfileNft(
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<Contents<CommonProfileNft>> {
        return handleApi { didaApi.getCommonProfileNft(page, size, sort).toDomain() }
    }
}

