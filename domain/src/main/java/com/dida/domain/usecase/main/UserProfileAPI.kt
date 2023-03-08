package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.UserProfile
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class UserProfileAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<UserProfile> {
        return repository.getUserProfileAPI()
    }
}