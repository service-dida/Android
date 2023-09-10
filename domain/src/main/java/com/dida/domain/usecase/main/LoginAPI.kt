package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.login.Token
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class LoginAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(idToken: String): NetworkResult<Token> {
        return repository.loginAPI(idToken)
    }
}
