package com.dida.data.api

import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MainAPIService {
    @GET("/app/version")
    suspend fun checkVersion(): Response<AppVersionResponse>

    @POST("/kakao/login")
    suspend fun loginAPIServer(@Body idToken : String): Response<LoginResponseModel>

    @POST("/user/nickname")
    suspend fun nicknameAPIServer(@Body nickName: String): Response<NicknameResponseModel>

    @POST("/new/user")
    suspend fun createuserAPIServer(@Body request: CreateUserRequestModel): Response<LoginResponseModel>

    @GET("/user")
    suspend fun getUserProfile() : Response<UserProfileResponseModel>

    @POST("/login/refresh")
    suspend fun refreshtokenAPIServer(@Header("refreshToken") request: String): Response<LoginResponseModel>

    @GET("/user/cards")
    suspend fun getUserCards() : Response<List<UserCardsResponseModel>>
}