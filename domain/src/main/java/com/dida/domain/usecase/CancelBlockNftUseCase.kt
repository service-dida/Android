package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CancelBlockNftUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nftId: Long) : NetworkResult<Unit> {
        return repository.cancelBlockNft(nftId)
    }
}
