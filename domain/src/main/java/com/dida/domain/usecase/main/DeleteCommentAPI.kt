package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class DeleteCommentAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(commentId: Long): NetworkResult<Unit> {
        return repository.patchCommentIdStatus(commentId = commentId)
    }
}
