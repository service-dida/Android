package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class ChangePasswordAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(beforePassword: String, afterPassword: String) : NetworkResult<Unit> {
        return repository.postChangePasswordAPI(beforePassword, afterPassword)
    }
}