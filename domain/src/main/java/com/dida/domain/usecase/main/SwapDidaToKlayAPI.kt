package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class SwapDidaToKlayAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(payPwd: String, dida: Double): NetworkResult<Unit> {
        return repository.postSwapDidaToKlay(payPwd, dida)
    }
}