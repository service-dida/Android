package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Password
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String) : NetworkResult<Password> {
        return repository.checkPassword(payPwd)
    }
}
