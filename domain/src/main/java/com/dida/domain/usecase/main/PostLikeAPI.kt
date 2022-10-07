package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class PostLikeAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(cardId: Long) : NetworkResult<Unit> {
        return repository.postLikeAPI(cardId)
    }
}