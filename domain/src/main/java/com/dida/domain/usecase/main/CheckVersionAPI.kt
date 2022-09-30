package com.dida.domain.usecase.main

import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject


class CheckVersionAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): Response<AppVersionResponse> {
        return repository.checkVersionAPI()
    }
}