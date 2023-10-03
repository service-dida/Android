package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CreateWalletUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, checkPwd: String) : NetworkResult<Unit> {
        return repository.createWallet(payPwd, checkPwd)
    }
}
