package com.dida.domain.main

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.MemberProfile

interface MainRepository {

    suspend fun login(idToken: String): NetworkResult<LoginToken>
    suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken>
    suspend fun checkNickname(nickname: String): NetworkResult<Boolean>
    suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken>
    suspend fun emailAuth(): NetworkResult<String>
    suspend fun checkWallet(): NetworkResult<Boolean>
    suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit>
    suspend fun deleteUser(): NetworkResult<Unit>
    suspend fun commonProfile(): NetworkResult<CommonProfile>
    suspend fun commonProfileNft(page: Int, size: Int, sort: String): NetworkResult<Contents<CommonProfileNft>>
    suspend fun memberProfile(memberId: Long): NetworkResult<MemberProfile>
    suspend fun memberProfileNFt(memberId: Long, page: Int, size: Int, sort: String): NetworkResult<Contents<CommonProfileNft>>
    suspend fun memberFollow(memberId: Long): NetworkResult<Unit>
}
