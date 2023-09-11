package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class BlockMemberUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(memberId: Long) : NetworkResult<Unit> {
        return repository.blockMember(memberId)
    }
}
