package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.Home
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class HomeAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<Home> {
        return repository.getMainAPI()
    }
}