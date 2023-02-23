package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class DeleteNftAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(cardId : Long) : NetworkResult<Long> {
        return repository.patchDeleteNft(cardId = cardId)
    }
}
