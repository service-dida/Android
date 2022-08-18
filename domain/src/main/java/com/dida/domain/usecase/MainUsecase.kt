package com.dida.domain.usecase

import com.dida.domain.BaseResponse
import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import retrofit2.Response


interface MainUsecase {
    suspend fun checkVersionAPI(): Response<AppVersionResponse>

    suspend fun loginAPI(idToken : String): Response<LoginResponseModel>

    suspend fun nicknameAPI(nickName: String): Response<NicknameResponseModel>

    suspend fun createUserAPI(request: CreateUserRequestModel): Response<LoginResponseModel>

    suspend fun getUserProfileAPI() : Response<UserProfileResponseModel>

    suspend fun refreshTokenAPI(request: String): Response<LoginResponseModel>

    suspend fun getUserCardsAPI() : Response<List<UserCardsResponseModel>>

    suspend fun getSendEmailAPI() : RandomNumber?

    suspend fun postCreateWalletAPI(password: String, passwordCheck: String) : Boolean

    suspend fun getWalletExistsAPI() : Boolean?

    suspend fun getCheckPasswordAPI(password: String) : Int

    suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String) : Boolean

    suspend fun getTempPasswordAPI() : Boolean

    suspend fun getMainAPI() : BaseResponse

    suspend fun getSoldOutAPI(term: Int) : BaseResponse
}