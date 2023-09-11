package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class WritePostUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nftId: Long, title: String, content: String) : NetworkResult<Unit> {
        return repository.writePost(nftId, title, content)
    }
}
