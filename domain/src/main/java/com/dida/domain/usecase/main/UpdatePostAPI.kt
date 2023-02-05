package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class UpdatePostAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(postId : Long, title: String, content: String) : NetworkResult<Unit> {
        return repository.patchPostPostId(postId = postId, title = title, content = content)
    }
}
