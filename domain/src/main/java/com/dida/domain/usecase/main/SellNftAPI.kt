package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class SellNftAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(password: String, cardId: Long, price : Double) : NetworkResult<Unit> {
        return repository.postSellNftAPI(password, cardId, price)
    }
}