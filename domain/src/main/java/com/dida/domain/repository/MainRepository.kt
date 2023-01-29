package com.dida.domain.repository

import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.model.nav.post.Post
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.model.nav.swap.WalletAmount
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

    suspend fun mintNFT(payPwd : String, name : String, description : String, image : String) : NetworkResult<Unit>

    suspend fun updateProfileImageAPI(file : MultipartBody.Part) : NetworkResult<Unit>

    suspend fun updateProfileDescriptionAPI(description: MultipartBody.Part) : NetworkResult<Unit>

    suspend fun updateProfileNicknameAPI(nickname: MultipartBody.Part) : NetworkResult<Unit>

    suspend fun postLikeAPI(cardId: Long) : NetworkResult<Unit>

    suspend fun postUserFollowAPI(userId: Long) : NetworkResult<Unit>

    suspend fun getDetailNFT(cardId: Long) : NetworkResult<DetailNFT>

    suspend fun putDeviceTokenAPI(deviceToken: String) : NetworkResult<Unit>

    suspend fun postBuyNftAPI(password: String, nftId: Long) : NetworkResult<Unit>

    suspend fun postSwapKlayToDida(password: String, klay: Double) : NetworkResult<Unit>

    suspend fun postSwapDidaToKlay(password: String, dida: Double) : NetworkResult<Unit>

    suspend fun postSellNftAPI(payPwd : String, cardId : Long, price : Double) : NetworkResult<Unit>

    suspend fun getWalletAmountAPI() : NetworkResult<WalletAmount>

    suspend fun getPosts() : NetworkResult<List<Posts>>

    suspend fun getPostPostId(postId: Int) : NetworkResult<Post>

    suspend fun getCommentsPostId(postId: Int) : NetworkResult<List<Comments>>
}
