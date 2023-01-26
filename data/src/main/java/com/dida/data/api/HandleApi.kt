package com.dida.data.api

import com.dida.data.model.*
import com.dida.domain.NetworkResult

internal inline fun <T> handleApi(transform: () -> T): NetworkResult<T> = try {
    NetworkResult.Success(transform.invoke())
} catch (e: Exception) {
    when (e) {
        is HaveNotJwtTokenException -> NetworkResult.Error(HaveNotJwtTokenException(e.cause, e.url, 100))
        is InvalidJwtTokenException -> NetworkResult.Error(InvalidJwtTokenException(e.cause, e.url, 102))
        is InvalidKakaoAccessTokenException -> NetworkResult.Error(InvalidKakaoAccessTokenException(e.cause, e.url, 104))
        is NotUseNicknameException -> NetworkResult.Error(NotUseNicknameException(e.cause, e.url, 109))
        is AlreadyEmailException -> NetworkResult.Error(AlreadyEmailException(e.cause, e.url, 110))
        is InvalidUserException -> NetworkResult.Error(InvalidUserException(e.cause, e.url, 111))
        is InvalidPasswordException -> NetworkResult.Error(InvalidPasswordException(e.cause, e.url, 114))
        is InvalidNftException -> NetworkResult.Error(InvalidNftException(e.cause, e.url, 115))
        is AlreadyWalletException -> NetworkResult.Error(AlreadyWalletException(e.cause, e.url, 117))
        is NotCorrectPasswordException -> NetworkResult.Error(NotCorrectPasswordException(e.cause, e.url, 118))
        is NeedToWalletException -> NetworkResult.Error(NeedToWalletException(e.cause, e.url, 119))
        is InvalidPeriodException -> NetworkResult.Error(InvalidPeriodException(e.cause, e.url, 120))
        is WrongPassword5TimesException -> NetworkResult.Error(WrongPassword5TimesException(e.cause, e.url, 121))
        is EmptyDeviceTokenException -> NetworkResult.Error(EmptyDeviceTokenException(e.cause, e.url, 124))
        is AlreadyUseWallet -> NetworkResult.Error(AlreadyUseWallet(e.cause, e.url, 125))
        is NeedMoreKlay -> NetworkResult.Error(NeedMoreKlay(e.cause, e.url, 127))
        is InvalidLengthException -> NetworkResult.Error(InvalidLengthException(e.cause, e.url, 200))
        is ServerNotFoundException -> NetworkResult.Error(ServerNotFoundException(e.cause, e.url, 404))
        is InternalServerErrorException -> NetworkResult.Error(InternalServerErrorException(e.cause, e.url, 500))
        else -> NetworkResult.Error(e)
    }
}
