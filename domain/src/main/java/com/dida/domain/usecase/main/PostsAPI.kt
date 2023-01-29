package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class PostsAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<List<Posts>> {
        return repository.getPosts()
    }
}
