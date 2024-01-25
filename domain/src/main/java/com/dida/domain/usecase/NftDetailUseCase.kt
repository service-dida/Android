package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.Nft
import javax.inject.Inject

class NftDetailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nftId: Long) : NetworkResult<Nft> {
        return repository.nftDetail(nftId)
    }
}
