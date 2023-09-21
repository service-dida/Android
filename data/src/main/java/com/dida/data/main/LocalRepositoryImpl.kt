package com.dida.data.main

import android.provider.ContactsContract.Data
import com.dida.data.DataApplication
import com.dida.data.api.handleApi
import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
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

    override suspend fun login(accessToken: String, refreshToken: String): NetworkResult<Unit> {
        return handleApi { DataApplication.dataStorePreferences.setAccessToken(accessToken, refreshToken) }
    }

    override suspend fun setUserId(userId: Long): NetworkResult<Unit> {
        return handleApi { DataApplication.dataStorePreferences.setUserId(userId) }
    }

    override suspend fun getToken(): NetworkResult<Pair<String?, String?>> {
        val accessToken: String? = DataApplication.dataStorePreferences.getAccessToken()
        val refreshToken: String? = DataApplication.dataStorePreferences.getRefreshToken()
        return handleApi { Pair(accessToken, refreshToken) }
    }
}

