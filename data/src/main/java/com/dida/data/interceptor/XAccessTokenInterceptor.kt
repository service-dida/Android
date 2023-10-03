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

        var accessToken = ""
        runBlocking {
            dataStorePreferences.getAccessToken()?.let {
                accessToken = it
            }
        }

        builder.addHeader("Authorization", accessToken)
        return chain.proceed(builder.build())
    }
}
