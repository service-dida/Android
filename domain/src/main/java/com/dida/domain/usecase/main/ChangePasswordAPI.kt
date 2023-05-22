package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class ChangePasswordAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(nowPwd: String, checkPwd: String) : NetworkResult<Unit> {
        return repository.postChangePasswordAPI(nowPwd, checkPwd)
    }
}