package com.dida.domain.main

import com.dida.domain.NetworkResult
import com.dida.domain.main.model.Keywords

interface LocalRepository {

    suspend fun checkLogin(): NetworkResult<Boolean>

    suspend fun logOut(): NetworkResult<Unit>

    suspend fun getUserId(): NetworkResult<Long>

    suspend fun login(accessToken: String?, refreshToken: String?): NetworkResult<Unit>

    suspend fun setUserId(userId: Long): NetworkResult<Unit>

    suspend fun getToken(): NetworkResult<Pair<String?, String?>>

    suspend fun setKeywords(keywords: Keywords): NetworkResult<Unit>

    suspend fun getKeywordThings(): NetworkResult<List<String>>

    suspend fun getKeywordPlaces(): NetworkResult<List<String>>

}

