package com.dida.domain.main

import com.dida.domain.NetworkResult

interface LocalRepository {

    suspend fun checkLogin(): NetworkResult<Boolean>

}

