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
    errorResponse: ErrorResponseImpl?
): Exception =
    when (errorResponse?.code) {
        "AUTH_005" -> Auth005Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "ALARM_001" -> Alarm001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "GLOBAL_001" -> Global001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "AUTH_001" -> Auth001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "AUTH_002" -> Auth002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "AUTH_003" -> Auth003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "AUTH_004" -> Auth004Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "GLOBAL_006" -> Global006Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_001" -> Wallet001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_006" -> Wallet006Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "GLOBAL_002" -> Global002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MARKET_002" -> Market002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MARKET_003" -> Market003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MARKET_004" -> Market004Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MARKET_005" -> Market005Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MEMBER_005" -> Member005Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "NFT_005" -> Nft005Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_002" -> Wallet002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_005" -> Wallet005Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MARKET_006" -> Market006Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "GLOBAL_003" -> Global003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "POST_001" -> Post001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "POST_002" -> Post002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "REPORT_001" -> Report001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "REPORT_002" -> Report002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "COMMENT_001" -> Comment001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "COMMENT_002" -> Comment002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "HIDE_001" -> Hide001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "HIDE_002" -> Hide002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "HIDE_003" -> Hide003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MARKET_001" -> Market001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MEMBER_001" -> Member001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MEMBER_003" -> Member003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "MEMBER_004" -> Member004Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "NFT_001" -> Nft001Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_004" -> Wallet004Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_008" -> Wallet008Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "GLOBAL_004" -> Global004Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        "GLOBAL_005" -> Global005Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "AUTH_006" -> Auth006Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "NFT_002" -> Nft002Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "NFT_003" -> Nft003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "NFT_004" -> Nft004Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_003" -> Wallet003Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_009" -> Wallet009Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_010" -> Wallet010Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_011" -> Wallet011Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)
        "WALLET_012" -> Wallet012Exception(Throwable(message = (errorResponse.code + errorResponse.message)), url, errorResponse.code)

        else -> UnknownException(Throwable("알 수 없는 에러가 발생했어요."), url, errorResponse?.code)
    }
