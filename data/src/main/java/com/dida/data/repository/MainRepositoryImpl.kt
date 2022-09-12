package com.dida.data.repository

import android.util.Log
import com.dida.data.api.MainAPIService
import com.dida.data.api.handleApi
import com.dida.data.mapper.*
import com.dida.data.model.createwallet.PostCheckPasswordRequest
import com.dida.data.model.createwallet.PostCreateWalletRequest
import com.dida.data.model.userInfo.PostPasswordChangeRequest
import com.dida.domain.BaseResponse
import com.dida.domain.ErrorResponse
import com.dida.domain.NetworkResult
import com.dida.domain.State
import com.dida.domain.model.login.CreateUserRequestModel
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.usecase.MainUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainAPIService: MainAPIService
    ): MainUsecase{

    override suspend fun checkVersionAPI(): Response<AppVersionResponse> {
        return mainAPIService.checkVersion()
    }

    override suspend fun loginAPI(idToken: String): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.loginAPIServer(idToken = idToken) }
    }

    override suspend fun nicknameAPI(nickName: String): NetworkResult<NicknameResponseModel> {
        return handleApi { mainAPIService.nicknameAPIServer(nickName) }
    }

    override suspend fun createUserAPI(request: CreateUserRequestModel): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.createuserAPIServer(request) }
    }

    override suspend fun getUserProfileAPI(): NetworkResult<UserProfileResponseModel> {
        return handleApi { mainAPIService.getUserProfile() }
    }

    override suspend fun refreshTokenAPI(request: String): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.refreshtokenAPIServer(request) }
    }

    override suspend fun getUserCardsAPI(): NetworkResult<List<UserCardsResponseModel>> {
        return handleApi { mainAPIService.getUserCards() }
    }

    override suspend fun getSendEmailAPI(): NetworkResult<String> {
        return handleApi { mainAPIService.getSendEmail().random }
    }

    override suspend fun getMainAPI(): NetworkResult<Flow<Home>> {
        return handleApi {
            flow {
                emit(mapperMainResponseToMain(mainAPIService.getMain()) )
            }.flowOn(Dispatchers.IO) }
    }

    override suspend fun getSoldOutAPI(term: Int): NetworkResult<Flow<List<SoldOut>>> {
        return handleApi {
            flow {
                emit(mapperSoldOutResponseToSoldOut(mainAPIService.getSoldOut(term)))
            }.flowOn(Dispatchers.IO) }
    }

    override suspend fun postCreateWalletAPI(
        password: String,
        passwordCheck: String,
    ): NetworkResult<Unit> {
        val request = PostCreateWalletRequest(password, passwordCheck)
        return handleApi { mainAPIService.postCreateWallet(request) }
    }

    override suspend fun getWalletExistsAPI(): NetworkResult<Boolean> {
        return handleApi { mainAPIService.getWalletExists().existed }
    }

    override suspend fun getCheckPasswordAPI(password: String): NetworkResult<Boolean> {
        val request = PostCheckPasswordRequest(password)
        return handleApi { mainAPIService.postCheckPassword(request).flag }
    }

    override suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String): NetworkResult<Unit> {
        val request = PostPasswordChangeRequest(nowPwd = beforePassword, changePwd = afterPassword)
        return handleApi { mainAPIService.postPasswordChange(request) }
    }

    override suspend fun getTempPasswordAPI(): NetworkResult<Unit> {
        return handleApi { mainAPIService.getTempPassword() }
    }
}