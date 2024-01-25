package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class PatchPostUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(postId: Long, title: String, content: String) : NetworkResult<Unit> {
        return repository.patchPost(postId, title, content)
    }
}
