package com.dida.android.domain.usecase

import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.domain.model.splash.AppVersionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainAPIService {
    @GET("/app/version")
    suspend fun checkVersion(): Response<AppVersionResponse>

    @POST("/kakao/login")
    fun loginAPIServer(@Body idToken : String): Call<LoginResponseModel>
}