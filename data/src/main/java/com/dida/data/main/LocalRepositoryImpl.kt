package com.dida.data.main

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
}

