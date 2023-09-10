package com.dida.data.main

import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MainRemoteService {

    // 카카오 로그인
    @POST("/kakao/login")
    suspend fun login(@Body body: PostLoginRequest): PostLoginResponse

}
