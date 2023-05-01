package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.UserNft
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class UserCardUserIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(userId: Long, page: Int) : NetworkResult<List<UserNft>> {
        return repository.getUserCardsUserId(userId = userId, page = page)
    }
}
