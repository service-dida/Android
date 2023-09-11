package com.dida.data.main

import com.dida.data.model.dex.GetMemberSwapResponse
import com.dida.data.model.login.GetCommonWalletResponse
import com.dida.data.model.login.GetEmailAuthResponse
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostLoginResponse
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostNicknameResponse
import com.dida.data.model.login.PostUserRequest
import com.dida.data.model.main.GetRecentNftsResponse
import com.dida.data.model.market.GetNftResponse
import com.dida.data.model.profile.GetCommonFollowResponse
import com.dida.data.model.profile.GetCommonProfileNftResponse
import com.dida.data.model.profile.GetCommonProfileResponse
import com.dida.data.model.profile.GetMemberProfileResponse
import com.dida.data.model.profile.GetMemberWalletResponse
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
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DidaApi {

    /**
     * 로그인 및 회원가입
     **/
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

    // TODO : PUBLICK KEY 발급 받기 API 추가

    // 인증 메일 보내기
    @GET("/visitor/auth")
    suspend fun getEmailAuth(): GetEmailAuthResponse

    // TODO : 지갑 발급하기 API 추가

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

    // 팔로우 목록 보기
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

    // TODO : 결제 비밀번호 수정하기 API 추가

    // 결제 비밀번호 찾기(임시 비밀번호 발급)
    @PATCH("/member/password/tmp")
    suspend fun patchTempMemberPassword(): Unit

    /**
     * Dex 및 Nft
     **/

    // TODO : NFT 만들기 API 추가

    // NFT 삭제하기
    @DELETE("/member/nft/{nftId}")
    suspend fun deleteNft(@Path("nftId") nftId: Long): Unit

    // TODO : KLAY → DIDA 스왑 API 추가

    // TODO : DIDA → KLAY 스왑 스왑 API 추가

    // 스왑 내역 확인하기
    @GET("/member/swap")
    suspend fun getMemberSwap(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetMemberSwapResponse

    // TODO : NFT 외부 전송하기 API 추가

    // TODO : KLAY 외부 전송하기 API 추가

    // TODO : NFT 판매하기 API 추가

    // TODO : NFT 판매 취소하기 API 추가

    // TODO : NFT 구매하기 API 추가

    // NFT 상세보기
    @GET("/common/nft/{nftId}")
    suspend fun getNftDetail(nftId: Long): GetNftResponse

    // TODO : 전체 거래내역 보기 API 추가

    // TODO : 판매 내역 확인하기 API 추가

    // TODO : 구매 내역 확인하기 API 추가

    /**
     * 메인 화면
     **/

    // TODO : 메인화면(Sold Out 제외) API 추가

    // TODO : 메인 화면 Sold Out 조회 API 추가

    // TODO : Sold Out 더보기 API 추가

    // TODO : Hot Seller 더보기 API 추가

    // 최신 NFT 더보기
    @GET("/recent-nfts")
    suspend fun getRecentNfts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetRecentNftsResponse

    // TODO : 활발한 활동 더보기 API 추가

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
}
