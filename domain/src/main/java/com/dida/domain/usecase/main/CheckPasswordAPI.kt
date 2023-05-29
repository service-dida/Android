package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.PasswordVerify
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class CheckPasswordAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(password: String) : NetworkResult<PasswordVerify> {
        return repository.getCheckPasswordAPI(password)
    }
}