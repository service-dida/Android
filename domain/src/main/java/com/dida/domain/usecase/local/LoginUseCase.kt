package com.dida.domain.usecase.local

import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) : NetworkResult<Unit> {
        return repository.login(accessToken, refreshToken)
    }
}
