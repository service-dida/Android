package com.dida.data.repository

import com.dida.data.api.MainAPIService
import com.dida.data.model.createwallet.SendEmailResponse
import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainAPIService: MainAPIService){
    suspend fun checkVersion() = mainAPIService.checkVersion()

    suspend fun loginAPIServer(idToken : String) = mainAPIService.loginAPIServer(idToken = idToken)

    suspend fun nicknameAPIServer(nickName : String): Response<NicknameResponseModel> {
        return mainAPIService.nicknameAPIServer(nickName)
    }

    suspend fun createUserAPIServer(request: CreateUserRequestModel): Response<LoginResponseModel> {
        return mainAPIService.createuserAPIServer(request)
    }

    suspend fun getUserProfile() : Response<UserProfileResponseModel> {
        return mainAPIService.getUserProfile()
    }

    suspend fun getUserCards() : Response<List<UserCardsResponseModel>>{
        return mainAPIService.getUserCards()
    }

    suspend fun refreshTokenAPIServer(request: String): Response<LoginResponseModel> {
        return mainAPIService.refreshtokenAPIServer(request)
    }

    suspend fun sendEmailAPIServer(): Response<SendEmailResponse> {
        return mainAPIService.getSendEmail()
    }
}