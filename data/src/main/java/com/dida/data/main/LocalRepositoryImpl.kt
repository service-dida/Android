package com.dida.data.main

import android.provider.ContactsContract.Data
import com.dida.data.DataApplication
import com.dida.data.api.handleApi
import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import com.dida.domain.main.model.Keywords
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
): LocalRepository {

    override suspend fun checkLogin(): NetworkResult<Boolean> {
        DataApplication.dataStorePreferences.getAccessToken()?.let {
            return handleApi { true }
        }
        return handleApi { false }
    }

    override suspend fun logOut(): NetworkResult<Unit> {
        return handleApi { DataApplication.dataStorePreferences.removeAccountToken() }
    }

    override suspend fun getUserId(): NetworkResult<Long> {
        return handleApi { DataApplication.dataStorePreferences.getUserId() }
    }

    override suspend fun login(accessToken: String?, refreshToken: String?): NetworkResult<Unit> {
        return if (accessToken != null && refreshToken != null) handleApi { DataApplication.dataStorePreferences.setAccessToken(accessToken, refreshToken) }
        else handleApi { DataApplication.dataStorePreferences.removeAccountToken() }
    }

    override suspend fun setUserId(userId: Long): NetworkResult<Unit> {
        return handleApi { DataApplication.dataStorePreferences.setUserId(userId) }
    }

    override suspend fun getToken(): NetworkResult<Pair<String?, String?>> {
        val accessToken: String? = DataApplication.dataStorePreferences.getAccessToken()
        val refreshToken: String? = DataApplication.dataStorePreferences.getRefreshToken()
        return handleApi { Pair(accessToken, refreshToken) }
    }

    override suspend fun setKeywords(keywords: Keywords): NetworkResult<Unit> {
        return handleApi { DataApplication.dataStorePreferences.setKeywords(keywords) }
    }

    override suspend fun getKeywordThings(): NetworkResult<List<String>> {
        return handleApi { DataApplication.dataStorePreferences.getKeywordThings() }
    }

    override suspend fun getKeywordPlaces(): NetworkResult<List<String>> {
        return handleApi { DataApplication.dataStorePreferences.getKeywordPlaces() }
    }
}

