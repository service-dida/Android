package com.dida.domain.repository

import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.model.splash.AppVersionResponse
import okhttp3.MultipartBody

interface  MainRepository {

    suspend fun checkVersionAPI(): NetworkResult<AppVersionResponse>

    suspend fun loginAPI(idToken : String): NetworkResult<LoginResponseModel>

    suspend fun nicknameAPI(nickName: String): NetworkResult<NicknameResponseModel>

    suspend fun createUserAPI(email: String, nickName: String): NetworkResult<LoginResponseModel>

    suspend fun getUserProfileAPI() : NetworkResult<UserProfile>

    suspend fun refreshTokenAPI(request: String): NetworkResult<LoginResponseModel>

    suspend fun getUserCardsAPI() : NetworkResult<List<UserNft>>

    suspend fun getSendEmailAPI() : NetworkResult<RandomNumber>

    suspend fun postCreateWalletAPI(password: String, passwordCheck: String) : NetworkResult<Unit>

    suspend fun getWalletExistsAPI() : NetworkResult<Boolean>

    suspend fun getCheckPasswordAPI(password: String) : NetworkResult<Boolean>

    suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String) : NetworkResult<Unit>

    suspend fun getTempPasswordAPI() : NetworkResult<Unit>

    suspend fun getMainAPI() : NetworkResult<Home>

    suspend fun getSoldOutAPI(term: Int) : NetworkResult<List<SoldOut>>

    suspend fun mintNFT(name : String, description : String, image : String) : NetworkResult<Unit>

    suspend fun updateProfileAPI(description: MultipartBody.Part, file : MultipartBody.Part) : NetworkResult<Unit>

    suspend fun postLikeAPI(cardId: Long) : NetworkResult<Unit>
}