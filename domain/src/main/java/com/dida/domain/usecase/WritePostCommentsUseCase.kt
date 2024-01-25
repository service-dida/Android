package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class WritePostCommentsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(postId: Long, content: String) : NetworkResult<Unit> {
        return repository.writePostComments(postId, content)
    }
}
