package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.SwapHistory
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class SwapHistoryAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<List<SwapHistory>> {
        return repository.getSwapHistoryAPI()
    }
}