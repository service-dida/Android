package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.post.Post
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class PostIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(postId: Long) : NetworkResult<Post> {
        return repository.getPostPostId(postId = postId)
    }
}
