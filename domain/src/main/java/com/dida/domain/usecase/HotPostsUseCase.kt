package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.HotPost
import com.dida.domain.main.model.Post
import javax.inject.Inject

class HotPostsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<Contents<HotPost>> {
        return repository.hotPosts(page, size)
    }
}
