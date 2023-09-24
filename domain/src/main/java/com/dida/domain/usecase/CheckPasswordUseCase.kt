package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String) : NetworkResult<Unit> {
        return repository.checkPassword(payPwd)
    }
}
