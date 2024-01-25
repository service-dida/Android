package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.PublicKey
import javax.inject.Inject

class PublicKeyUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<PublicKey> {
        return repository.getPublicKey()
    }
}
