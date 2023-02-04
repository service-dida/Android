package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class CommentsPostIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(postId: Long) : NetworkResult<List<Comments>> {
        return repository.getCommentsPostId(postId = postId)
    }
}
