package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.WalletAmount
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class WalletAmountAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<WalletAmount> {
        return repository.getWalletAmountAPI()
    }
}