package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class UserCardUserIdAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(userId: Long) : NetworkResult<List<UserNft>> {
        return repository.getUserCardsUserId(userId = userId)
    }
}
