package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class BlockCommentUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(commentId: Long) : NetworkResult<Unit> {
        return repository.blockComment(commentId)
    }
}
