package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.`swap-history`.SwapHistory
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class SwapHistoryAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<SwapHistory> {
        return repository.getSwapHistoryAPI()
    }
}