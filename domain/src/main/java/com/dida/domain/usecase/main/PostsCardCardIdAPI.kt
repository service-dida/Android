package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class PostsCardCardIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(cardId: Long) : NetworkResult<List<Posts>> {
        return repository.getPostsCardCardId(cardId = cardId)
    }
}
