package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.OwnershipHistory
import javax.inject.Inject

class GetOwnershipHistoryUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nftId: Long, page: Int, pageSize: Int) : NetworkResult<Contents<OwnershipHistory>> {
        return repository.getOwnershipHistory(nftId, page, pageSize)
    }
}
