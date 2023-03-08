package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.OtherUserProfie
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class UserUserIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(userId: Long) : NetworkResult<OtherUserProfie> {
        return repository.getUserUserId(userId = userId)
    }
}
