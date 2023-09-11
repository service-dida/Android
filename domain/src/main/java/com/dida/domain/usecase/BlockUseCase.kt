package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Block
import javax.inject.Inject

class BlockUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(type: Block, blockId: Long) : NetworkResult<Unit> {
        return repository.block(type, blockId)
    }
}
