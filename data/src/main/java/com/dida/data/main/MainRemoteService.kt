package com.dida.data.main

import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostLoginResponse
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostNicknameResponse
import com.dida.data.model.login.PostUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface MainRemoteService {

    // 카카오 로그인
    @POST("/kakao/login")
    suspend fun login(@Body body: PostLoginRequest): PostLoginResponse

    // 회원가입
    @POST("/user")
    suspend fun postUser(@Body body: PostUserRequest): PostLoginResponse

    // 닉네임 체크
    @POST("/nickname")
    suspend fun postNickname(@Body body: PostNicknameRequest): PostNicknameResponse
}
