package com.dida.data.api

import com.dida.data.model.*
import com.dida.domain.NetworkResult

internal inline fun <T> handleApi(transform: () -> T): NetworkResult<T> = try {
    NetworkResult.Success(transform.invoke())
} catch (e: Exception) {
    when (e) {
        is ServerNotFoundException -> NetworkResult.Error(ServerNotFoundException(e.cause, e.url, 404))
        is InternalServerErrorException -> NetworkResult.Error(InternalServerErrorException(e.cause, e.url, 500))
        is UnknownException -> NetworkResult.Error(UnknownException(e.cause, e.url, 999))
        else -> NetworkResult.Error(e)
    }
}
