package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CancelBlockUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(postId: Long) : NetworkResult<Unit> {
        return repository.blockPost(postId)
    }
}
