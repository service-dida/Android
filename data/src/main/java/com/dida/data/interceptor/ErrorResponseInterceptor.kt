package com.dida.data.interceptor

import android.util.Log
import com.dida.data.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ErrorResponseInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        try {
            val response = chain.proceed(request)
            val responseBody = response.body

            if (response.isSuccessful) return response

            val requestUrl = request.url.toString()
            val errorResponse = responseBody?.string()?.let { createErrorResponse(it) }
            val errorException = createErrorException(requestUrl, response.code, errorResponse)
            errorException?.let { throw it }

            return response
        } catch (e: Throwable) {
            /**
             * Non-IOException subtypes thrown from interceptor never notify Callback
             * See https://github.com/square/okhttp/issues/5151
             */

            when(e) {
                is IOException -> throw e
                else -> throw IOException(e)
            }
        }
    }
}

fun createErrorResponse(responseBodyString: String): ErrorResponseImpl? =
    try {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter(ErrorResponseImpl::class.java).fromJson(responseBodyString)
    } catch (e: Exception) {
        Log.d("response1234", "1111")
        null
    }

private fun createErrorException(url: String?, httpCode: Int, errorResponse: ErrorResponseImpl?): Exception? =
    when(errorResponse?.code) {
        100 -> HaveNotJwtTokenException(Throwable(errorResponse.message), url, 100)
        102 -> InvalidJwtTokenException(Throwable(errorResponse.message), url, 102)
        104 -> InvalidKakaoAccessTokenException(Throwable(errorResponse.message), url, 104)
        109 -> NotUseNicknameException(Throwable(errorResponse.message), url, 109)
        110 -> AlreadyEmailException(Throwable(errorResponse.message), url, 110)
        111 -> InvalidUserException(Throwable(errorResponse.message), url, 111)
        115 -> InvalidNftException(Throwable(errorResponse.message), url, 115)
        117 -> AlreadyWalletException(Throwable(errorResponse.message), url, 117)
        118 -> NotCorrectPasswordException(Throwable(errorResponse.message), url, 118)
        119 -> NeedToWalletException(Throwable(errorResponse.message), url, 119)
        120 -> InvalidPeriodException(Throwable(errorResponse.message), url, 120)
        200 -> InvalidLengthException(Throwable(errorResponse.message), url, 200)
        404 -> ServerNotFoundException(Throwable(errorResponse?.message), url, 404)
        500 -> InternalServerErrorException(Throwable(errorResponse?.message), url, 500)
        else -> null
    }
