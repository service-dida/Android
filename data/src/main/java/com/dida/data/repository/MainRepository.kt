package com.dida.data.repository

import android.util.Log
import com.dida.data.api.MainAPIService
import com.dida.data.mapper.*
import com.dida.data.model.createwallet.PostCheckPasswordRequest
import com.dida.data.model.createwallet.PostCreateWalletRequest
import com.dida.data.model.userInfo.PostPasswordChangeRequest
import com.dida.domain.BaseResponse
import com.dida.domain.State
import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.usecase.MainUsecase
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainAPIService: MainAPIService
    ): MainUsecase{

    override suspend fun checkVersionAPI(): Response<AppVersionResponse> {
        return mainAPIService.checkVersion()
    }

    override suspend fun loginAPI(idToken: String): Response<LoginResponseModel> {
        return mainAPIService.loginAPIServer(idToken = idToken)
    }

    override suspend fun nicknameAPI(nickName: String): Response<NicknameResponseModel> {
        return mainAPIService.nicknameAPIServer(nickName)
    }

    override suspend fun createUserAPI(request: CreateUserRequestModel): Response<LoginResponseModel> {
        return mainAPIService.createuserAPIServer(request)
    }

    override suspend fun getUserProfileAPI(): Response<UserProfileResponseModel> {
        return mainAPIService.getUserProfile()
    }

    override suspend fun refreshTokenAPI(request: String): Response<LoginResponseModel> {
        return mainAPIService.refreshtokenAPIServer(request)
    }

    override suspend fun getUserCardsAPI(): Response<List<UserCardsResponseModel>> {
        return mainAPIService.getUserCards()
    }

    override suspend fun getSendEmailAPI(): RandomNumber? {
        var result: RandomNumber? = null
        val response = mainAPIService.getSendEmail()

        if(response.isSuccessful && response.body() != null) {
            result = mapperSendEmailResponseToRandomNum(response.body()!!)
        }
        return result
    }

    override suspend fun getMainAPI(): BaseResponse {
        val response = mainAPIService.getMain()
        Log.d("error_response!!!", response.toString())

        return if(response.isSuccessful && response.body() != null) {
            BaseResponse(State.SUCCESS, mapperMainResponseToMain(response.body()!!))
        } else {
            BaseResponse(State.NETWORK_ERROR, response.errorBody())
        }
    }

    override suspend fun getSoldOutAPI(term: Int): BaseResponse {
        val response = mainAPIService.getSoldOut(term)

        return if(response.isSuccessful && response.body() != null) {
            BaseResponse(State.SUCCESS, mapperSoldOutResponseToSoldOut(response.body()!!))
        } else {
            BaseResponse(State.NETWORK_ERROR, response.errorBody())
        }
    }

    override suspend fun postCreateWalletAPI(
        password: String,
        passwordCheck: String,
    ): Boolean {
        var result = false
        val request = PostCreateWalletRequest(password, passwordCheck)
        val response = mainAPIService.postCreateWallet(request)

        if(response.isSuccessful) {
            result = true
        }
        return result
    }

    override suspend fun getWalletExistsAPI(): Boolean? {
        val response = mainAPIService.getWalletExists()
        var result: Boolean? = null

        if(response.isSuccessful && response.body() != null) {
            result = response.body()!!.existed
        }
        return result
    }

    override suspend fun getCheckPasswordAPI(password: String): Int {
        val request = PostCheckPasswordRequest(password)
        val response = mainAPIService.postCheckPassword(request)
        var result = 0

        if(response.isSuccessful && response.body() != null) {
            result = if(response.body()!!.flag) 1 else 2
        }
        return result
    }

    override suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String): Boolean {
        val request = PostPasswordChangeRequest(nowPwd = beforePassword, changePwd = afterPassword)
        val response = mainAPIService.postPasswordChange(request)
        return response.isSuccessful && response.body() == "200"
    }

    override suspend fun getTempPasswordAPI(): Boolean {
        val response = mainAPIService.getTempPassword()

        return response.isSuccessful && response.body() == "200"
    }
}