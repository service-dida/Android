package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class HideCancelNftAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(nftId: Long) : NetworkResult<Unit> {
        return repository.getHideNft(nftId)
    }
}