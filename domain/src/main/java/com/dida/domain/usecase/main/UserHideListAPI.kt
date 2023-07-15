package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.UserHide
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class UserHideListAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<List<UserHide>> {
        return repository.getUserHideList()
    }
}
