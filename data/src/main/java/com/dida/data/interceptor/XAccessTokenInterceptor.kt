package com.dida.data.interceptor

import com.dida.data.DataApplication.Companion.dataStorePreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

//        val jwtToken : String? = mySharedPreferences.getAccessToken()
        var jwtToken = ""
        runBlocking {
            dataStorePreferences.getAccessToken()?.let {
                jwtToken = it
            }
        }

        builder.addHeader("Authorization", jwtToken)
        return chain.proceed(builder.build())
    }
}