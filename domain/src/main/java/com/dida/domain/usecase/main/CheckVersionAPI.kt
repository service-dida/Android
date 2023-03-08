package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.AppVersion
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class CheckVersionAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<AppVersion> {
        return repository.checkVersionAPI()
    }
}