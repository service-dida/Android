package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class SwapToKlayUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, coin: Int) : NetworkResult<Unit> {
        return repository.swapToKlay(payPwd, coin)
    }
}
