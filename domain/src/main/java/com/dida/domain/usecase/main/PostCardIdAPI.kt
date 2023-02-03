package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class PostCardIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(cardId : Long, title: String, content: String) : NetworkResult<Unit> {
        return repository.postPostCardId(cardId = cardId, title = title, content = content)
    }
}
