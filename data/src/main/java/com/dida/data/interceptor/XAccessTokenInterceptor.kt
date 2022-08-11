package com.dida.data.interceptor

import com.dida.data.DataApplication.Companion.mySharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken : String? = mySharedPreferences.getAccessToken()

        if (jwtToken != null) {
            builder.addHeader("Authorization", jwtToken)
        }
        else {
            builder.addHeader("Authorization", "")
        }
        return chain.proceed(builder.build())
    }
}