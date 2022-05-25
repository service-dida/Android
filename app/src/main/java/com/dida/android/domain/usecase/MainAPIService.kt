package com.dida.android.domain.usecase

import com.dida.android.domain.model.splash.AppVersionResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainAPIService {
    @GET("/app/version")
    suspend fun checkVersion(): Response<AppVersionResponse>
}