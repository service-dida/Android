package com.dida.data.model

import java.io.IOException


class ServerNotFoundException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // status 404 서버 못참음
class InternalServerErrorException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // status 500 서버 터짐

class HaveNotJwtTokenException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // Jwt 토큰이 없습니다. 100
class InvalidJwtTokenException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // Jwt가 유효하지 않습니다. 102
class InvalidKakaoAccessTokenException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 카카오 ID 토큰이 유효하지 않습니다. 104

class EmptyDeviceTokenException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // DEVICE TOKEN은 빈칸일 수 없습니다. 124

class NotUseNicknameException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 사용할 수 없는 닉네임 109
class AlreadyEmailException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 이미 사용중인 이메일 110
class InvalidUserException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 올바르지 않은 사용자 111
class InvalidPasswordException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 비밀번호가 올바르지 않습니다 114
class InvalidNftException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 유효하지 않은 NFT 115
class AlreadyWalletException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 이미 지갑이 존재 117
class NotCorrectPasswordException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 비밀번호가 일치하지 않습니다. 118
class NeedToWalletException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 지갑이 필요합니다 119
class InvalidPeriodException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 기간이 잘못되었습니다. 120
class WrongPassword5TimesException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e)//비밀번호를 5회 이상 잘못 입력하였습니다. 121
class AlreadyUseWallet(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 지난 지갑 사용후 3분이 안됨. 125
class NeedMoreKlay(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 보유한 클레이가 부족. 127
class NeedLogin(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 보유한 클레이가 부족. 127
class InvalidLengthException(e: Throwable?, val url: String? = null, code: Int?) : IOException(e) // 닉네임 길이 & 비밀번호 길이가 잘못 되었습니다. 200
