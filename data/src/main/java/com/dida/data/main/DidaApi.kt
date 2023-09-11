package com.dida.data.main

import com.dida.data.model.login.GetCommonWalletResponse
import com.dida.data.model.login.GetEmailAuthResponse
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostLoginResponse
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostNicknameResponse
import com.dida.data.model.login.PostUserRequest
import com.dida.data.model.profile.GetCommonFollowResponse
import com.dida.data.model.profile.GetCommonProfileNftResponse
import com.dida.data.model.profile.GetCommonProfileResponse
import com.dida.data.model.profile.GetMemberProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DidaApi {

    /** 로그인 및 회원가입 **/
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

    /** 프로필 관련 **/
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
}
