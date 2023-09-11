package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CheckWalletUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<Boolean> {
        return repository.checkWallet()
    }
}
