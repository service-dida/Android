package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class SellNftUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, nftId: Long, price: Double) : NetworkResult<Unit> {
        return repository.sellNft(payPwd, nftId, price)
    }
}
