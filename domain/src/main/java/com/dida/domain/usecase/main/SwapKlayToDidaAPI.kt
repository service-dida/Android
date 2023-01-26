package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class SwapKlayToDidaAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(payPwd: String, klay: Double): NetworkResult<Unit> {
        return repository.postSwapKlayToDida(payPwd, klay)
    }
}