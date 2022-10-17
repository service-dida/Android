package com.dida.data.api

import com.dida.data.model.*
import com.dida.domain.NetworkResult
import java.io.IOException

internal inline fun <T> handleApi(transform: () -> T): NetworkResult<T> = try {
    NetworkResult.Success(transform.invoke())
} catch (e: Exception) {
    when (e) {
        is HaveNotJwtTokenException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 100))
        is InvalidJwtTokenException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 102))
        is InvalidKakaoAccessTokenException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 104))
        is NotUseNicknameException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 109))
        is AlreadyEmailException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 110))
        is InvalidUserException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 111))
        is InvalidNftException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 115))
        is AlreadyWalletException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 117))
        is NotCorrectPasswordException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 118))
        is NeedToWalletException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 119))
        is InvalidPeriodException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 120))
        is EmptyDeviceTokenException -> NetworkResult.Error(EmptyDeviceTokenException(e.cause, e.url, 124))
        is InvalidLengthException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 200))
        is ServerNotFoundException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 404))
        is InternalServerErrorException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 500))
        else -> NetworkResult.Error(e)
    }
}