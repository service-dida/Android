package com.dida.data.interceptor

import android.util.Log
import com.dida.data.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.math.log

class ErrorResponseInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        try {
            val response = chain.proceed(request)
            val responseBody = response.body

            if (response.isSuccessful) return response

            val requestUrl = request.url.toString()
            val errorResponse = responseBody?.string()?.let { createErrorResponse(it) }
            Log.d("haha", "intercept: ${response.code}")
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

private fun createErrorResponse(responseBodyString: String): ErrorResponseImpl? =
    try {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter(ErrorResponseImpl::class.java).fromJson(responseBodyString)
    } catch (e: Exception) {
        null
    }

private fun createErrorException(url: String?, httpCode: Int, errorResponse: ErrorResponseImpl?): Exception? =
    when (httpCode) {
//        400, 403 -> BadRequestException(Throwable(errorResponse?.message), url)
        400, 401, 403 -> InvalidKakaoAccessTokenException(Throwable(errorResponse?.message), url)
        402 -> AccountNotFoundException(Throwable(errorResponse?.message), url)
        404 -> ServerNotFoundException(Throwable(errorResponse?.message), url)
        500 -> InternalServerErrorException(Throwable(errorResponse?.message), url)
        else -> null
    }