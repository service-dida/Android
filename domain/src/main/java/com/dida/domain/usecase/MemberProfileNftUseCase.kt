package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.Sort
import javax.inject.Inject

class MemberProfileNftUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(memberId: Long, page: Int, pageSize: Int, sort: Sort) : NetworkResult<Contents<CommonProfileNft>> {
        return repository.memberProfileNFt(memberId, page, pageSize, sort)
    }
}
