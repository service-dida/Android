package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class CheckVersionAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<AppVersionResponse> {
        return repository.checkVersionAPI()
    }
}