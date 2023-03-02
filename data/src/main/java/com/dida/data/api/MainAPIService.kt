package com.dida.data.api

import com.dida.data.model.request.*
import com.dida.data.model.response.*
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
    suspend fun loginAPIServer(@Body idToken: String): LoginResponseModel

    @POST("/user/nickname")
    suspend fun nicknameAPIServer(@Body postNicknameRequest: PostNicknameRequest): NicknameResponseModel

    @POST("/new/user")
    suspend fun createuserAPIServer(@Body request: CreateUserRequest): LoginResponseModel

    @GET("/user")
    suspend fun getUserProfile(): UserProfileResponse

    @GET("/login/refresh")
    suspend fun refreshtokenAPIServer(@Header("refreshToken") request: String): LoginResponseModel

    @GET("/user/cards")
    suspend fun getUserCards(): List<UserNft>

    @GET("/auth/mail")
    suspend fun getSendEmail(): SendEmailResponse

    @GET("/main")
    suspend fun getMain(): GetMainResponse

    @GET("/main/{term}")
    suspend fun getSoldOut(@Path("term") term: Int): List<GetSoldOutResponse>

    @POST("/user/wallet")
    suspend fun postCreateWallet(@Body request: PostCreateWalletRequest)

    @GET("/user/wallet")
    suspend fun getWalletExists(): GetWalletExistsResponse

    @POST("/user/wallet/pwd/check")
    suspend fun postCheckPassword(@Body request: PostCheckPasswordRequest): PostCheckPasswordResponse

    @POST("/user/wallet/pwd")
    suspend fun postPasswordChange(@Body request: PostPasswordChangeRequest)

    // 임시 비밀번호 발급(카카오 계정으로 등록된 이메일)
    @GET("/user/wallet/pwd")
    suspend fun getTempPassword()

    @POST("/card")
    suspend fun mintNFT(@Body request: NFTMintRequest): Long

    @Multipart
    @PUT("/user/img")
    suspend fun updateProfileImage(@Part file: MultipartBody.Part): Unit

    @PUT("/user/name")
    suspend fun updateProfileNickname(@Body request: PutUserNicknameRequest): Unit

    @PUT("/user/description")
    suspend fun updateProfileDescription(@Body request: PutUserDescriptionRequest): Unit

    @POST("/card/like")
    suspend fun postLike(@Body request: PostLikeRequest): Unit

    @POST("/user/follow")
    suspend fun postUserFollow(@Body request: PostUserFollowRequest): Unit

    @GET("/card/{cardId}")
    suspend fun getDetailNFT(@Path("cardId") cardId: String): GetDetailNFTResponse

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
    suspend fun getWalletAmount(): GetWalletAmountResponse

    @GET("/swap-list")
    suspend fun getSwapHistory(): List<GetSwapHistoryResponse>

    // 커뮤니티 게시글 전체
    @GET("posts/{page}")
    suspend fun getPosts(@Path("page") page: Int): List<GetPostsResponse>

    // 게시글 상세 가져오기
    @GET("post/{postId}")
    suspend fun getPostPostId(@Path("postId") postId: Long): GetPostPostIdResponse

    // 게시글 답변 가져오기
    @GET("comments/{postId}")
    suspend fun getCommentsPostId(@Path("postId") postId: Long): List<GetPostIdCommentsResponse>

    // 커뮤니티 생성 목록(내가 좋아요한 NFT)
    @GET("cards/post/like")
    suspend fun getCardsPostLike(): List<GetCardsPostResponse>

    // 커뮤니티 생성 목록(내가 보유한 NFT)
    @GET("cards/post/my")
    suspend fun getCardsPostMy(): List<GetCardsPostResponse>

    // 커뮤니티 생성
    @POST("post/{cardId}")
    suspend fun postPostCardId(
        @Path("cardId") cardId: Long,
        @Body body: PostPostCardIdRequest
    ): Unit

    // 커뮤니티 수정
    @PATCH("post/{postId}")
    suspend fun patchPostPostId(
        @Path("postId") postId: Long,
        @Body body: PostPostCardIdRequest
    ): Unit

    // 커뮤니티 삭제
    @PATCH("post/{postId}/status")
    suspend fun patchPostPostIdStatus(@Path("postId") postId: Long): Unit

    // NFT상세 커뮤니티
    @GET("posts/card/{cardId}")
    suspend fun getPostsCardCardId(@Path("cardId") cardId: Long): List<GetPostsResponse>

    // 커뮤니티 시끌벅적 게시판
    @GET("hot/cards")
    suspend fun getHotCards(): List<GetHotCardsResponse>

    // 게시글 댓글 작성하기
    @POST("comment")
    suspend fun postComment(@Body body: PostCommentRequest): Unit

    // 게시글 댓글 삭제하기
    @PATCH("comment/{commentId}/status")
    suspend fun patchCommentIdStatus(@Path("commentId") commentId: Long): Unit

    // 전체 거래 내역 가져오기
    @GET("buy-sell-list")
    suspend fun getBuySellList(): List<GetBuySellListResponse>

    // 전체 거래 내역 가져오기
    @GET("buy-list")
    suspend fun getBuyList(): List<GetBuySellListResponse>

    // 전체 거래 내역 가져오기
    @GET("sell-list")
    suspend fun getSellList(): List<GetBuySellListResponse>

    // 최신 NFT 더보기
    @GET("recent/card/{page}")
    suspend fun getRecentCard(@Path("page") page: Int): List<RecentCardResponse>

    //NFT 목록 조회
    @GET("card/hideList")
    suspend fun getHideList(): List<CardHideListResponse>

    //NFT 숨기기
    @GET("card/hide/{cardId}")
    suspend fun getHideNft(@Path("cardId") cardId: Long): Unit

    //NFT 숨기기 취소
    @GET("card/reveal/{cardId}")
    suspend fun getHideCancelNft(@Path("cardId") cardId: Long): Unit

    // 타 유저 프로필 조회
    @GET("/user/{userId}")
    suspend fun getUserUserId(@Path("userId") userId: Long): GetUserUserIdResponse

    // 타 유저 카드 조회
    @GET("/user/cards/{userId}")
    suspend fun getUserCardsUserId(@Path("userId") userId: Long): List<RecentCardResponse>

    @PATCH("/card/status/{cardId}")
    suspend fun patchDeleteNft(@Path("cardId") cardId: Long): Long

    // 활발한 활동 더보기
    @GET("/hot/user/{page}")
    suspend fun getHotUser(@Path("page") page: Int): List<GetHotUserResponse>
}
