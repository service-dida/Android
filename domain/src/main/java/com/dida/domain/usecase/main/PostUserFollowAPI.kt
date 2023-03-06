package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class PostUserFollowAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(userId: Int) : NetworkResult<Unit> {
        return repository.postUserFollowAPI(userId)
    }
}