package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class EmailAuthUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<String> {
        return repository.emailAuth()
    }
}
