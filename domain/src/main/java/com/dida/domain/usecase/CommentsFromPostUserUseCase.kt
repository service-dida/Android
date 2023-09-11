package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Comment
import com.dida.domain.main.model.LoginToken
import javax.inject.Inject

class CommentsFromPostUserUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(postId: Long, page: Int, size: Int) : NetworkResult<Contents<Comment>> {
        return repository.getCommentsFromPost(postId, page, size)
    }
}
