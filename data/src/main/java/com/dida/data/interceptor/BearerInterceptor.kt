package com.dida.data.interceptor

import com.dida.data.BuildConfig
import com.dida.data.DataApplication
import com.dida.data.api.ApiClient.RELEASE_URL
import com.dida.data.api.ApiClient.TEST_URL
import com.dida.data.api.MainAPIService
import com.dida.data.api.handleApi
import com.dida.data.model.InvalidJwtTokenException
import com.dida.domain.onError
import com.dida.domain.onSuccess
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   * 사용시 주석 풀고 사용하기
*/

class BearerInterceptor : Interceptor {
    //todo 조건 분기로 인터셉터 구조 변경
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = ""
        val request = chain.request()
        val response = chain.proceed(request)

        when(response.code) {
            in 400 .. 500 -> {
                val requestUrl = request.url.toString()
                val errorResponse = response.body?.string()?.let { createErrorResponse(it) }
                val errorException = createErrorException(requestUrl, response.code, errorResponse)

                when(errorException) {
                    is InvalidJwtTokenException -> {
                        runBlocking {
                            //토큰 갱신 api 호출
                            DataApplication.dataStorePreferences.getRefreshToken()?.let {
                                handleApi {
                                    if (BuildConfig.DEBUG) {
                                        Retrofit.Builder()
                                            .baseUrl(TEST_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build()
                                            .create(MainAPIService::class.java).refreshtokenAPIServer(it)
                                    } else {
                                        Retrofit.Builder()
                                            .baseUrl(RELEASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build()
                                            .create(MainAPIService::class.java).refreshtokenAPIServer(it)
                                    }
                                }
                            }?.onSuccess {
                                DataApplication.dataStorePreferences.setAccessToken(it.accessToken ?: "", it.refreshToken ?: "")
                                accessToken = it.accessToken ?: ""
                            }?.onError {
                                DataApplication.dataStorePreferences.removeAccountToken()
                                accessToken = ""
                            }
                        }
                        return chain.proceed(chain.request().newBuilder().addHeader("Authorization", accessToken).build())
                    }
                    else -> errorException.let { throw it }
                }


            }

            else -> return response
        }
    }
}
