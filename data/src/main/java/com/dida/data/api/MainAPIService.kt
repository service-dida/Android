package com.dida.data.api

import com.dida.data.model.createwallet.PostCreateWalletRequest
import com.dida.data.model.createwallet.SendEmailResponse
import com.dida.data.model.main.GetMainResponse
import com.dida.data.model.main.GetSoldOutResponse
import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import retrofit2.Response
import retrofit2.http.*

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

    @GET("/auth/mail")
    suspend fun getSendEmail() : Response<SendEmailResponse>

    @GET("/main")
    suspend fun getMain() : Response<GetMainResponse>

    @GET("/main/{term}")
    suspend fun getSoldOut(@Path("term") term: Int) : Response<List<GetSoldOutResponse>>

    @POST("/user/wallet")
    suspend fun postCreateWallet(@Body request: PostCreateWalletRequest) : Response<Unit>
}