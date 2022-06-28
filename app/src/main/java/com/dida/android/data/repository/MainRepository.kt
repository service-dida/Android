package com.dida.android.data.repository

import com.dida.android.domain.model.login.CreateUserRequestModel
import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.domain.model.login.NicknameResponseModel
import com.dida.android.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.android.domain.usecase.MainAPIService
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

    suspend fun getUserProfile() : Response<UserProfileResponseModel>{
        return mainAPIService.getUserProfile()
    }
}