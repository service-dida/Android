package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.Token
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class CreateUserAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(email: String, nickName: String): NetworkResult<Token> {
        return repository.createUserAPI(email, nickName)
    }
}