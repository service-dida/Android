package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class BuyNftUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, marketId: Long) : NetworkResult<Unit> {
        return repository.buyNft(payPwd, marketId)
    }
}
