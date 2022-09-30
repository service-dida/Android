package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class CreateWalletAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(password: String, passwordCheck: String) : NetworkResult<Unit> {
        return repository.postCreateWalletAPI(password, passwordCheck)
    }
}