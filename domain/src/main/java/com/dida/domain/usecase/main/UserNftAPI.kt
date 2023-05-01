package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.UserNft
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class UserNftAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(page: Int) : NetworkResult<List<UserNft>> {
        return repository.getUserCardsAPI(page = page)
    }
}
