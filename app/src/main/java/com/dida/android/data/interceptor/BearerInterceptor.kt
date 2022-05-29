package com.dida.android.data.interceptor

import com.dida.android.GlobalApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

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
        var Baseresponse = chain.request()
        if(Baseresponse.method == "HTTP 403 "){
            var accessToken = GlobalApplication.mySharedPreferences.getAccessToken()
//            var refreshToken = RunnerBeApplication.sSharedPreferences.getString("refresh-token", null)

//            val response = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(RefreshAPI::class.java)

//            val request = PostRefreshRequest(accessToken!!, refreshToken!!)
//            val result = response.postRefresh(request)
//
//            val editor = MyApplication.editor
//            editor.putString("X-ACCESS-TOKEN", result.blockingGet().accessToken)
//            editor.putString("refresh-token", result.blockingGet().refreshToken)
//            editor.commit()
//
//            accessToken = result.blockingGet().accessToken
            val newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer ${accessToken}")
                .build()
            return chain.proceed(newRequest)
        }
        else{
            val response = chain.request()
            return chain.proceed(chain.request())
        }
    }
}