package com.dida.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import com.dida.data.BuildConfig

class KlaytnAuthInterceptor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val authenticatedRequest = chain.request()
            .newBuilder()
            .header("Authorization", BuildConfig.KLAYTN_HEADER_AUTHORIZATION)
            //.header("x-chain-id", "8217") // Main Net
            .header("x-chain-id", "1001") // Test Net
            .build()

        return chain.proceed(authenticatedRequest)
    }
}