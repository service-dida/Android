package com.dida.domain.main

import com.dida.domain.NetworkResult
import com.dida.domain.main.model.LoginToken

interface  MainRepository {

    suspend fun login(idToken : String): NetworkResult<LoginToken>
}
