package com.dida.data.api

import com.dida.data.model.createwallet.*
import com.dida.data.model.detail.GetDetailNFTResponse
import com.dida.data.model.device.PutDeviceTokenRequest
import com.dida.data.model.klaytn.NFTMintRequest
import com.dida.data.model.login.CreateUserRequestModel
import com.dida.data.model.main.GetMainResponse
import com.dida.data.model.main.GetSoldOutResponse
import com.dida.data.model.main.PostLikeRequest
import com.dida.data.model.main.PostUserFollowRequest
import com.dida.data.model.mypage.UserProfileResponse
import com.dida.data.model.nickname.PostNicknameRequest
import com.dida.data.model.swap_history.GetSwapHistoryResponse
import com.dida.data.model.post.*
import com.dida.data.model.swap.GetWalletAmountResponse
import com.dida.data.model.swap.PostSwapDidaToKlayRequest
import com.dida.data.model.swap.PostSwapKlayToDidaRequest
import com.dida.data.model.tradenft.PostBuyNftRequest
import com.dida.data.model.tradenft.PostSellNftRequest
import com.dida.data.model.userInfo.PostPasswordChangeRequest
import com.dida.data.model.userInfo.PutUserDescriptionRequest
import com.dida.data.model.userInfo.PutUserNicknameRequest
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

    @GET("/login/refresh")
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
    @PUT("/user/img")
    suspend fun updateProfileImage(@Part file: MultipartBody.Part): Unit

    @PUT("/user/name")
    suspend fun updateProfileNickname(@Body request: PutUserNicknameRequest): Unit

    @PUT("/user/description")
    suspend fun updateProfileDescription(@Body request: PutUserDescriptionRequest): Unit

    @POST("/card/like")
    suspend fun postLike(@Body request: PostLikeRequest) : Unit

    @POST("/user/follow")
    suspend fun postUserFollow(@Body request: PostUserFollowRequest): Unit

    @GET("/card/{cardId}")
    suspend fun getDetailNFT(@Path("cardId")cardId : String) : GetDetailNFTResponse

    @PUT("/device/token")
    suspend fun putDeviceToken(@Body request: PutDeviceTokenRequest): Unit

    @POST("/market/card")
    suspend fun postBuyNft(@Body request: PostBuyNftRequest): Unit

    @POST("/swap/1")
    suspend fun postSwapKlayToDida(@Body request: PostSwapKlayToDidaRequest): Unit

    @POST("/swap/2")
    suspend fun postSwapDidaToKlay(@Body request: PostSwapDidaToKlayRequest): Unit

    @POST("/market")
    suspend fun postSellNft(@Body request: PostSellNftRequest): Unit

    @GET("/wallet")
    suspend fun getWalletAmount() : GetWalletAmountResponse

    @GET("/swap-list")
    suspend fun getSwapHistory() : List<GetSwapHistoryResponse>

    // 커뮤니티 게시글 전체
    @GET("posts/{page}")
    suspend fun getPosts(@Path("page") page: Int) : List<GetPostsResponse>

    // 게시글 상세 가져오기
    @GET("post/{postId}")
    suspend fun getPostPostId(@Path("postId") postId: Int) : GetPostPostIdResponse

    // 게시글 답변 가져오기
    @GET("comments/{postId}")
    suspend fun getCommentsPostId(@Path("postId") postId: Int) : List<GetPostIdCommentsResponse>

    // 커뮤니티 생성 목록(내가 좋아요한 NFT)
    @GET("cards/post/like")
    suspend fun getCardsPostLike() : List<GetCardsPostResponse>

    // 커뮤니티 생성 목록(내가 보유한 NFT)
    @GET("cards/post/my")
    suspend fun getCardsPostMy() : List<GetCardsPostResponse>

    // 커뮤니티 생성 목록
    @POST("post/{cardId}")
    suspend fun postPostCardId(@Path("cardId") cardId: Long, @Body body: PostPostCardIdRequest) : Unit

    // NFT상세 커뮤니티
    @GET("posts/card/{cardId}")
    suspend fun getPostsCardCardId(@Path("cardId") cardId: Long) : List<GetPostsResponse>

    // 커뮤니티 시끌벅적 게시판
    @GET("hot/cards")
    suspend fun getHotCards() : List<GetHotCardsResponse>

    // 게시글 댓글 작성하기
    @POST("comment")
    suspend fun postComment(@Body body: PostCommentRequest) : Unit

    // 게시글 댓글 삭제하기
    @PATCH("comment/{commentId}/status")
    suspend fun patchCommentIdStatus(@Path("commentId") commentId: Long) : Unit

}
