package com.dida.domain.usecase.local

import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import javax.inject.Inject

class SetUserIdUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke(userId: Long) : NetworkResult<Unit> {
        return repository.setUserId(userId)
    }
}
