package com.dida.android.domain.usecase

import com.dida.android.domain.model.login.CreateUserRequestModel
import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.domain.model.login.NicknameResponseModel
import com.dida.android.domain.model.splash.AppVersionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainAPIService {
    @GET("/app/version")
    suspend fun checkVersion(): Response<AppVersionResponse>

    @POST("/kakao/login")
    suspend fun loginAPIServer(@Body idToken : String): Response<LoginResponseModel>

    @POST("/user/nickname")
    suspend fun nicknameAPIServer(@Body nickName: String): Response<NicknameResponseModel>

    @POST("/new/user")
    suspend fun createuserAPIServer(@Body request: CreateUserRequestModel): Response<LoginResponseModel>
}