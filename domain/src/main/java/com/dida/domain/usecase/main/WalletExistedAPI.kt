package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class WalletExistedAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<Boolean> {
        return repository.getWalletExistsAPI()
    }
}