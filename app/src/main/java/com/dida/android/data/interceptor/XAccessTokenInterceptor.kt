package com.dida.android.data.interceptor

import com.dida.android.GlobalApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken : String? = GlobalApplication.mySharedPreferences.getAccessToken()

        //val refreshToken: String? = GlobalApplication.mySharedPreferences.getRefreshToken()

        if (jwtToken != null) {
            builder.addHeader("Authorization", jwtToken)
            /*if(refreshToken != null){
                builder.addHeader("Authorization", "Bearer "+jwtToken)
                builder.addHeader("refresh-token", refreshToken!!)
            }*/
        }
        return chain.proceed(builder.build())
    }
}