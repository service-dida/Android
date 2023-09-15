package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class SwapToDidaUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, coin: Int) : NetworkResult<Unit> {
        return repository.swapToDida(payPwd, coin)
    }
}
