package com.dida.data.api

import com.dida.data.model.createwallet.*
import com.dida.data.model.detail.GetDetailNFTResponse
import com.dida.data.model.klaytn.NFTMintRequest
import com.dida.data.model.login.CreateUserRequestModel
import com.dida.data.model.main.GetMainResponse
import com.dida.data.model.main.GetSoldOutResponse
import com.dida.data.model.main.PostLikeRequest
import com.dida.data.model.main.PostUserFollowRequest
import com.dida.data.model.mypage.UserProfileResponse
import com.dida.data.model.nickname.PostNicknameRequest
import com.dida.data.model.userInfo.PostPasswordChangeRequest
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.splash.AppVersionResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface MainAPIService {
    @GET("/app/version")
    suspend fun checkVersion(): AppVersionResponse

    @POST("/kakao/login")
    suspend fun loginAPIServer(@Body idToken : String): LoginResponseModel

    @POST("/user/nickname")
    suspend fun nicknameAPIServer(@Body postNicknameRequest: PostNicknameRequest): NicknameResponseModel

    @POST("/new/user")
    suspend fun createuserAPIServer(@Body request: CreateUserRequestModel): LoginResponseModel

    @GET("/user")
    suspend fun getUserProfile() : UserProfileResponse

    @POST("/login/refresh")
    suspend fun refreshtokenAPIServer(@Header("refreshToken") request: String): LoginResponseModel

    @GET("/user/cards")
    suspend fun getUserCards() : List<UserNft>

    @GET("/auth/mail")
    suspend fun getSendEmail() : SendEmailResponse

    @GET("/main")
    suspend fun getMain() : GetMainResponse

    @GET("/main/{term}")
    suspend fun getSoldOut(@Path("term") term: Int) : List<GetSoldOutResponse>

    @POST("/user/wallet")
    suspend fun postCreateWallet(@Body request: PostCreateWalletRequest)

    @GET("/user/wallet")
    suspend fun getWalletExists() : GetWalletExistsResponse

    @POST("/user/wallet/pwd/check")
    suspend fun postCheckPassword(@Body request: PostCheckPasswordRequest) : PostCheckPasswordResponse

    @POST("/user/wallet/pwd")
    suspend fun postPasswordChange(@Body request: PostPasswordChangeRequest)

    @GET("/user/wallet/pwd")
    suspend fun getTempPassword()

    @POST("/card")
    suspend fun mintNFT(@Body request: NFTMintRequest)

    @Multipart
    @PUT("/user")
    suspend fun updateProfile(@Part description: MultipartBody.Part, @Part file: MultipartBody.Part)

    @POST("/card/like")
    suspend fun postLike(@Body request: PostLikeRequest) : Unit

    @POST("/user/follow")
    suspend fun postUserFollow(@Body request: PostUserFollowRequest): Unit

    @GET("/card/{cardId}")
    suspend fun getDetailNFT(@Path("cardId")cardId : String) : GetDetailNFTResponse
}