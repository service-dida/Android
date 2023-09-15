package com.dida.data.model

import java.io.IOException


class UnknownException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // 서버 에러

class InvalidIdTokenException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // 400, 409

class InvalidTokenException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // 401 JWT 토큰 오류

class PermissionException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // 403 권한 및 비밀번호 오류

class ServerNotFoundException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // status 404 Url 오류 & 본인 X인 경우

class MethodException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // status 405 Http Method 오류

class InvalidMediaType(e: Throwable?, val url: String? = null, code: String?): IOException(e) // status 415 Media Type 오류

class InternalServerErrorException(e: Throwable?, val url: String? = null, code: String?): IOException(e) // status 500 서버 터짐
