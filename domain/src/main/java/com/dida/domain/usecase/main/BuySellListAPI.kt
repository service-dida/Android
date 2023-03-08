package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.TradeHistory
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class BuySellListAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<List<TradeHistory>> {
        return repository.getBuySellList()
    }
}
