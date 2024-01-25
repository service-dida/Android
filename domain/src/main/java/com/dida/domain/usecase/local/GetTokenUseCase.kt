package com.dida.domain.usecase.local

import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke() : NetworkResult<Pair<String?, String?>> {
        return repository.getToken()
    }
}
