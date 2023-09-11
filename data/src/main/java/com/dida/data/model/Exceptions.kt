package com.dida.data.model

import java.io.IOException


class UnknownException(e: Throwable?, val url: String? = null, code: Int?): IOException(e) // 서버 에러
class ServerNotFoundException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // status 404 서버 못참음
class InternalServerErrorException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // status 500 서버 터짐
