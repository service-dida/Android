package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class PostPostHideAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(postId: Long) : NetworkResult<Unit> {
        return repository.postPostHide(postId)
    }
}