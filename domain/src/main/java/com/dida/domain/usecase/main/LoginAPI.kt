package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class LoginAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(idToken: String): NetworkResult<LoginResponseModel> {
        return repository.loginAPI(idToken)
    }
}