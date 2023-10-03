package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Post
import javax.inject.Inject

class PostsDetailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(postId: Long) : NetworkResult<Post> {
        return repository.postDetail(postId)
    }
}
