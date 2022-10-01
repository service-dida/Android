package com.dida.data.repository

import com.dida.data.api.MainAPIService
import com.dida.data.api.handleApi
import com.dida.data.mapper.toDomain
import com.dida.data.model.createwallet.PostCheckPasswordRequest
import com.dida.data.model.createwallet.PostCreateWalletRequest
import com.dida.data.model.klaytn.NFTMintRequest
import com.dida.data.model.login.CreateUserRequestModel
import com.dida.data.model.nickname.PostNicknameRequest
import com.dida.data.model.userInfo.PostPasswordChangeRequest
import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.repository.MainRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun checkVersionAPI(): Response<AppVersionResponse> {
        return mainAPIService.checkVersion()
    }

    override suspend fun loginAPI(idToken: String): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.loginAPIServer(idToken = idToken) }
    }

    override suspend fun nicknameAPI(nickName: String): NetworkResult<NicknameResponseModel> {
        val request = PostNicknameRequest(nickName)
        return handleApi { mainAPIService.nicknameAPIServer(request) }
    }

    override suspend fun createUserAPI(email: String, nickName: String): NetworkResult<LoginResponseModel> {
        val request = CreateUserRequestModel(email, nickName)
        return handleApi { mainAPIService.createuserAPIServer(request) }
    }

    override suspend fun getUserProfileAPI(): NetworkResult<UserProfile> {
        return handleApi { mainAPIService.getUserProfile().toDomain() }
    }

    override suspend fun refreshTokenAPI(request: String): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.refreshtokenAPIServer(request) }
    }

    override suspend fun getUserCardsAPI(): NetworkResult<List<UserNft>> {
        return handleApi { mainAPIService.getUserCards() }
    }

    override suspend fun getSendEmailAPI(): NetworkResult<RandomNumber> {
        return handleApi { mainAPIService.getSendEmail().toDomain() }
    }

    override suspend fun getMainAPI(): NetworkResult<Home> {
        return handleApi { mainAPIService.getMain().toDomain() }
    }

    override suspend fun getSoldOutAPI(term: Int): NetworkResult<List<SoldOut>> {
        return handleApi { mainAPIService.getSoldOut(term).toDomain() }
    }

    override suspend fun mintNFT(name: String, description: String, image: String): NetworkResult<Unit> {
        val request = NFTMintRequest(name,description,image)
        return handleApi { mainAPIService.mintNFT(request) }
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
        return handleApi { mainAPIService.postCheckPassword(request).toDomain() }
    }

    override suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String): NetworkResult<Unit> {
        val request = PostPasswordChangeRequest(nowPwd = beforePassword, changePwd = afterPassword)
        return handleApi { mainAPIService.postPasswordChange(request) }
    }

    override suspend fun getTempPasswordAPI(): NetworkResult<Unit> {
        return handleApi { mainAPIService.getTempPassword() }
    }

    override suspend fun updateProfileAPI(description: MultipartBody.Part, file : MultipartBody.Part) : NetworkResult<Unit>{
        return handleApi { mainAPIService.updateProfile(description,file)}
    }
}