package com.dida.domain.usecase.local

import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke() : NetworkResult<Long> {
        return repository.getUserId()
    }
}
