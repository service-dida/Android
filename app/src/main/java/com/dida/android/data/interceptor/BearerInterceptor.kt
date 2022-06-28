package com.dida.android.data.interceptor

import android.util.Log
import com.dida.android.GlobalApplication
import com.dida.android.data.MySharedPreferences
import com.dida.android.data.repository.MainRepository
import com.dida.android.domain.model.login.LoginResponseModel
import com.dida.android.domain.usecase.MainAPIService
import com.dida.android.util.GlobalConstant.Companion.BASE_URL
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KParameter

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   * 사용시 주석 풀고 사용하기
*/

class BearerInterceptor: Interceptor {
    //todo 조건 분기로 인터셉터 구조 변경
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = ""
        if(chain.request().method == "HTTP 400 "){
            accessToken = runBlocking {
                //토큰 갱신 api 호출
                val request = GlobalApplication.mySharedPreferences.getRefreshToken()
                val response = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MainAPIService::class.java)
                    .refreshtokenAPIServer(request!!)

                GlobalApplication.mySharedPreferences
                    .setAccessToken(response.body()!!.accessToken, response.body()!!.refreshToken)
                response.body()?.accessToken?: "Empty Token"
            }

            val newRequest = chain.request().newBuilder().addHeader("Authorization", accessToken)
                .build()
            return chain.proceed(newRequest)
        }
        else{
            accessToken = runBlocking {
                GlobalApplication.mySharedPreferences.getAccessToken()!!
            }
        }

        val newRequest = chain.request().newBuilder().addHeader("Authorization", accessToken)
            .build()
        return chain.proceed(newRequest)
    }
}