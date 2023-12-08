package com.dida.data.main

import com.dida.data.model.additional.GetAlarmsResponse
import com.dida.data.model.additional.GetHideMembersResponse
import com.dida.data.model.additional.GetHideNftsResponse
import com.dida.data.model.additional.GetKeywordsResponse
import com.dida.data.model.additional.PostMakePictureRequest
import com.dida.data.model.additional.PostMakePictureResponse
import com.dida.data.model.additional.PostNftLikeRequest
import com.dida.data.model.additional.PostReportRequest
import com.dida.data.model.dex.DeleteSellNftRequest
import com.dida.data.model.dex.GetMemberSwapResponse
import com.dida.data.model.dex.GetTransactionInfoResponse
import com.dida.data.model.dex.GetTransactionsResponse
import com.dida.data.model.dex.PostBuyNftRequest
import com.dida.data.model.dex.PostNftRequest
import com.dida.data.model.dex.PostNftResponse
import com.dida.data.model.dex.PostSellNftRequest
import com.dida.data.model.dex.PostSwapRequest
import com.dida.data.model.login.GetCommonWalletResponse
import com.dida.data.model.login.GetEmailAuthResponse
import com.dida.data.model.login.GetPublicKeyResponse
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostCheckPasswordRequest
import com.dida.data.model.login.PostCheckPasswordResponse
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostLoginResponse
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostNicknameResponse
import com.dida.data.model.login.PostUserRequest
import com.dida.data.model.login.PostWalletRequest
import com.dida.data.model.main.GetHotSellerPageResponse
import com.dida.data.model.main.GetMainResponse
import com.dida.data.model.main.GetRecentNftsResponse
import com.dida.data.model.main.GetSoldOutPageResponse
import com.dida.data.model.main.GetSoldOutResponse
import com.dida.data.model.market.GetNftResponse
import com.dida.data.model.market.GetOwnershipHistoryResponse
import com.dida.data.model.profile.GetCommonFollowResponse
import com.dida.data.model.profile.GetCommonProfileNftResponse
import com.dida.data.model.profile.GetCommonProfileResponse
import com.dida.data.model.profile.GetMemberProfileResponse
import com.dida.data.model.profile.GetMemberWalletResponse
import com.dida.data.model.profile.PatchMemberPasswordRequest
import com.dida.data.model.sns.GetOwnNftsResponse
import com.dida.data.model.profile.PatchProfileDescriptionRequest
import com.dida.data.model.profile.PatchProfileNicknameRequest
import com.dida.data.model.sns.GetCommentsFromPostResponse
import com.dida.data.model.sns.GetHotPostsResponse
import com.dida.data.model.sns.GetPostDetailResponse
import com.dida.data.model.sns.GetPostsResponse
import com.dida.data.model.sns.PatchCommonPostRequest
import com.dida.data.model.sns.PostCommonCommentsRequest
import com.dida.data.model.sns.PostCommonPostRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DidaApi {

    // 카카오 로그인
    @POST("/kakao/login")
    suspend fun login(@Body body: PostLoginRequest): PostLoginResponse

    // 회원가입
    @POST("/user")
    suspend fun postUser(@Body body: PostUserRequest): PostLoginResponse

    // 닉네임 체크
    @POST("/nickname")
    suspend fun postNickname(@Body body: PostNicknameRequest): PostNicknameResponse

    // 로그인 토큰 갱신
    @PATCH("/common/refresh")
    suspend fun patchRefreshToken(@Header("refreshToken") request: String): PostLoginResponse

    // PUBLICK KEY 발급 받기
    @GET("/common/key")
    suspend fun getPublicKey(): GetPublicKeyResponse

    // 인증 메일 보내기
    @GET("/common/auth")
    suspend fun getEmailAuth(): GetEmailAuthResponse

    // 지갑 발급하기
    @POST("/visitor/wallet")
    suspend fun postWallet(@Body body: PostWalletRequest): Unit

    // 지갑 여부 확인
    @GET("/common/wallet")
    suspend fun getCommonWallet(): GetCommonWalletResponse

    // Device Token 바꾸기
    @PATCH("/member/device")
    suspend fun patchMemberDevice(@Body body: PatchMemberDeviceRequest): Unit

    // 계정 삭제하기
    @DELETE("/member")
    suspend fun deleteUser(): Unit

    /**
     * 프로필 관련
     **/
    // 내 프로필 확인하기
    @GET("/common/profile")
    suspend fun getCommonProfile(): GetCommonProfileResponse

    // 내 NFT 확인하기
    @GET("/common/profile/nft")
    suspend fun getCommonProfileNft(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String? = "updated_desc"
    ): GetCommonProfileNftResponse

    // 다른 유저 프로필 확인
    @GET("/profile/{memberId}")
    suspend fun getMemberProfile(@Path("memberId") memberId: Long): GetMemberProfileResponse

    // 다른 유저 NFT 확인하기
    @GET("/profile/nft/{memberId}")
    suspend fun getMemberProfileNft(
        @Path("memberId") memberId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String? = "updated_desc"
    ): GetCommonProfileNftResponse

    // 다른 유저 팔로우 누르기
    @PATCH("/common/follow/{memberId}")
    suspend fun patchMemberFollow(@Path("memberId") memberId: Long): Unit

    // 팔로우 목록 보기
    @GET("/common/follow")
    suspend fun getCommonFollow(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetCommonFollowResponse

    // 팔로잉 목록 보기
    @GET("/common/following")
    suspend fun getCommonFollowing(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetCommonFollowResponse

    // 내 지갑 확인하기
    @GET("/member/wallet")
    suspend fun getMemberWallet(): GetMemberWalletResponse

    // 프로필 이미지 변경
    @Multipart
    @PATCH("/common/image")
    suspend fun patchProfileImage(@Part file: MultipartBody.Part): Unit

    // 프로필 설명 수정
    @PATCH("/common/description")
    suspend fun patchProfileDescription(@Body body: PatchProfileDescriptionRequest): Unit

    // 프로필 닉네임 수정
    @PATCH("/common/nickname")
    suspend fun patchProfileNickname(@Body body: PatchProfileNicknameRequest): Unit

    // 결제 비밀번호 수정하기
    @PATCH("/member/password")
    suspend fun patchMemberPassword(@Body body: PatchMemberPasswordRequest): Unit

    // 결제 비밀번호 찾기(임시 비밀번호 발급)
    @PATCH("/member/password/tmp")
    suspend fun patchTempMemberPassword(): Unit

    // 결제 비밀번호 확인
    @POST("/member/password/check")
    suspend fun postCheckPassword(@Body body: PostCheckPasswordRequest): PostCheckPasswordResponse

    /**
     * Dex 및 Nft
     **/

    // NFT 만들기
    @POST("/member/nft")
    suspend fun postNft(@Body body: PostNftRequest): PostNftResponse

    // NFT 삭제하기
    @DELETE("/member/nft/{nftId}")
    suspend fun deleteNft(@Path("nftId") nftId: Long): Unit

    // KLAY → DIDA 스왑
    @POST("/member/klay")
    suspend fun postSwapToDida(@Body body: PostSwapRequest): Unit

    // DIDA → KLAY 스왑
    @POST("/member/dida")
    suspend fun postSwapToKlay(@Body body: PostSwapRequest): Unit

    // 스왑 내역 확인하기
    @GET("/member/swap")
    suspend fun getMemberSwap(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetMemberSwapResponse

    // TODO : NFT 외부 전송하기 API 추가

    // TODO : KLAY 외부 전송하기 API 추가

    // NFT 판매하기
    @POST("/member/market")
    suspend fun postSellNft(@Body body: PostSellNftRequest): Unit

    // NFT 판매 취소하기
    @HTTP(method = "DELETE", path="/member/market", hasBody = true)
    suspend fun deleteSellNft(@Body body: DeleteSellNftRequest): Unit

    // NFT 구매하기
    @POST("/member/market/nft")
    suspend fun postBuyNft(@Body body: PostBuyNftRequest): Unit

    // NFT 상세보기
    @GET("/nft/{nftId}")
    suspend fun getNftDetail(@Path("nftId") nftId: Long): GetNftResponse

    // 전체 거래 내역 보기
    @GET("/member/transaction")
    suspend fun getTransactionInfos(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetTransactionInfoResponse

    // 판매 내역 보기
    @GET("/member/transaction/sale")
    suspend fun getSaleTransactionInfos(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetTransactionsResponse

    // 구매 내역 보기
    @GET("/member/transaction/purchase")
    suspend fun getPurchaseTransactionInfos(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetTransactionsResponse

    /**
     * 메인 화면
     **/

    // 메인 화면(Sold Out 제외)
    @GET("/main")
    suspend fun getMain(): GetMainResponse

    // 메인 화면 Sold Out
    @GET("/main/sold-out")
    suspend fun getSoldOut(
        @Query("range") range: Int
    ): GetSoldOutResponse

    // Sold Out 더보기
    @GET("/sold-outs")
    suspend fun getSoldOutPage(
        @Query("range") range: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetSoldOutPageResponse

    // Hot Seller 더보기
    @GET("/hot-sellers")
    suspend fun getHotSellerPage(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetHotSellerPageResponse

    // 최신 NFT 더보기
    @GET("/recent-nfts")
    suspend fun getRecentNfts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetRecentNftsResponse

    // 활발한 활동 더보기
    @GET("/hot-members")
    suspend fun getHotMemberPage(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetHotSellerPageResponse

    /**
     * SNS 기능
     **/

    // 게시글 작성하기
    @POST("/common/posts")
    suspend fun writePost(@Body body: PostCommonPostRequest): Unit

    // 게시글 수정하기
    @PATCH("/common/posts")
    suspend fun patchPost(@Body body: PatchCommonPostRequest): Unit

    // 게시글 삭제하기
    @DELETE("/common/posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: Long): Unit

    // 최신 게시글 조회
    @GET("/posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetPostsResponse

    // NFT별 최신 게시글 조회
    @GET("/posts/nft/{nftId}")
    suspend fun getPostsFromNft(
        @Path("nftId") nftId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetPostsResponse

    // 게시글 상세 조회
    @GET("/posts/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
    ): GetPostDetailResponse


    // 댓글 작성하기
    @POST("/common/comments")
    suspend fun writePostComments(@Body body: PostCommonCommentsRequest): Unit

    // TODO : 댓글 수정하기 API 추가

    // 댓글 삭제하기
    @DELETE("/common/comments/{commentId}")
    suspend fun deletePostComments(@Path("commentId") commentId: Long): Unit

    // 게시글 별 댓글 조회
    @GET("/comments/{postId}")
    suspend fun getCommentsFromPost(
        @Path("postId") postId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetCommentsFromPostResponse

    // 시끌벅적 게시판 조회
    @GET("/posts/hot")
    suspend fun getHotPosts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetHotPostsResponse

    // 내가 보유한 NFT 목록 조회
    @GET("/common/nft/own")
    suspend fun getOwnNfts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetOwnNftsResponse

    // 내가 좋아요한 NFT 목록
    @GET("/common/nft/like")
    suspend fun getLikedNfts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetOwnNftsResponse

    /**
     * 추가 기능
     **/

    // NFT 숨기기
    @POST("/common/nft/hide")
    suspend fun blockNft(@Query("nftId") nftId: Long): Unit

    // NFT 숨기기 취소
    @DELETE("/common/nft/hide")
    suspend fun cancelBlockNft(@Query("nftId") nftId: Long): Unit

    // NFT 숨김 목록
    @GET("/common/nft/hide")
    suspend fun getHideNfts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetHideNftsResponse

    // 멤버 숨기기
    @POST("/common/member/hide")
    suspend fun blockMember(@Query("memberId") memberId: Long): Unit

    // 멤버 숨기기 취소
    @DELETE("/common/member/hide")
    suspend fun cancelBlockMember(@Query("memberId") memberId: Long): Unit

    // 멤버 숨김 목록 조회 API
    @GET("/common/member/hide")
    suspend fun getHideMembers(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetHideMembersResponse

    // 게시글 숨기기
    @POST("/common/post/hide")
    suspend fun blockPost(@Query("postId") postId: Long): Unit

    // TODO : 게시글 숨기기 취소 API
    @DELETE("/common/post/hide")
    suspend fun cancelBlockPost(@Query("postId") postId: Long): Unit

    // 댓글 숨기기
    @POST("/common/comment/hide")
    suspend fun blockComments(@Query("commentId") commentId: Long): Unit

    // NFT 신고하기
    @POST("/common/report/nft")
    suspend fun reportNft(@Body body: PostReportRequest): Unit

    // 멤버 신고하기
    @POST("/common/report/member")
    suspend fun reportMember(@Body body: PostReportRequest): Unit

    // 게시글 신고하기
    @POST("/common/report/post")
    suspend fun reportPost(@Body body: PostReportRequest): Unit

    // 댓글 신고하기
    @POST("/common/report/comment")
    suspend fun reportComment(@Body body: PostReportRequest): Unit

    // NFT 좋아요 누르기
    @POST("/common/nft/like")
    suspend fun postNftLike(@Body body: PostNftLikeRequest): Unit

    // 그림 생성하기
    @POST("/member/picture")
    suspend fun makeAiPicture(@Body body: PostMakePictureRequest): PostMakePictureResponse

    // 알림 확인하기
    @PATCH("/common/alarm/{alarmId}")
    suspend fun patchReadAlarm(@Path("alarmId") alarmId: Long): Unit

    // 알림 목록 조회하기
    @GET("/common/alarm")
    suspend fun getAlarms(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetAlarmsResponse

    // 키워드 목록 가져오기
    @GET("/keyword")
    suspend fun getKeywords(): GetKeywordsResponse

    //NFT소유권 변경 내역 확인하기
    @GET("/history/{nftId}")
    suspend fun getOwnershipHistory(
        @Path("nftId") nftId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetOwnershipHistoryResponse
}
