package com.dida.data.interceptor

import com.dida.data.model.*
import com.dida.data.model.InvalidTokenException
import com.google.gson.Gson
import java.io.IOException


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
        400, 409 -> InvalidIdTokenException(Throwable(errorResponse?.message), url, errorResponse?.code)
        401 -> InvalidTokenException(Throwable(errorResponse?.message), url, errorResponse?.code)
        403 -> PermissionException(Throwable(errorResponse?.message), url, errorResponse?.code)
        404 -> ServerNotFoundException(Throwable(errorResponse?.message), url, errorResponse?.code)
        405 -> MethodException(Throwable(errorResponse?.message), url, errorResponse?.code)
        415 -> InvalidMediaType(Throwable(errorResponse?.message), url, errorResponse?.code)
        500 -> InternalServerErrorException(Throwable(errorResponse?.message), url, errorResponse?.code)
        else -> UnknownException(Throwable("알 수 없는 에러가 발생했어요."), url, errorResponse?.code)
    }
