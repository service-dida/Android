package com.dida.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ErrorResponseImpl(
    @Json(name = "timestamp") override val timestamp: String?,
    @Json(name = "status") override val status: Int?,
    @Json(name = "message") override val message: String?,
    @Json(name = "code") override val code: Int?
) : ErrorResponse

//@JsonClass(generateAdapter = true)
//data class ErrorResponseImpl (
//    @Json(name = "timestamp") val timestamp: String?,
//    @Json(name = "status") val status: Int?,
//    @Json(name = "message") val message: String?,
//    @Json(name = "code") val code: Int?
//)

