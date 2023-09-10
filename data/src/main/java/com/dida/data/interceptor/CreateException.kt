package com.dida.data.interceptor

import com.dida.data.model.*
import com.google.gson.Gson


fun createErrorResponse(responseBodyString: String): ErrorResponseImpl? =
    try {
        Gson().newBuilder().create().getAdapter(ErrorResponseImpl::class.java)
            .fromJson(responseBodyString)
    } catch (e: Exception) {
        null
    }

fun createErrorException(
    url: String?,
    httpCode: Int,
    errorResponse: ErrorResponseImpl?
): Exception =
    when (httpCode) {
        404 -> ServerNotFoundException(Throwable(errorResponse?.message), url, 404)
        500 -> InternalServerErrorException(Throwable(errorResponse?.message), url, 500)
        else -> UnknownException(Throwable("알 수 없는 에러가 발생했어요."), url, 999)
    }
