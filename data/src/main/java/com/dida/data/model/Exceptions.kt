package com.dida.data.model

import java.io.IOException


class UnknownException(e: Throwable?, val url: String? = null, code: String? = null): IOException(e) // 서버 에러

class ServerNotFoundException(e: Throwable?, val url: String? = null, code: String? = null): IOException(e) // status 404 Url 오류 & 본인 X인 경우

class InternalServerErrorException(e: Throwable?, val url: String? = null, code: String? = null): IOException(e) // status 500 서버 터짐

/**
 * 400번 에러
 **/

/** AUTH_005 유효하지 않은 ID TOKEN 입니다. **/
class Auth005Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** ALARM_001 잘못된 ALARM ID 입니다. **/
class Alarm001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** GLOBAL_001 올바른 argument를 입력해주세요. **/
class Global001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_002 비밀번호가 일치하지 않습니다. **/
class Wallet002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 401번 에러
 **/

/** AUTH_001 JWT가 없습니다. **/
class Auth001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** AUTH_002 유효하지 않은 JWT입니다. **/
class Auth002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** AUTH_003 만료된 JWT입니다. **/
class Auth003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** AUTH_004 지원하지 않는 JWT입니다. **/
class Auth004Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 403번 에러
 **/

/** GLOBAL_006 권한이 없습니다. **/
class Global006Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_001 지갑이 없습니다. **/
class Wallet001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_006 비밀번호를 5번 이상 잘못 입력하였습니다. **/
class Wallet006Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 404번 에러
 **/

/** GLOBAL_002 올바른 URI로 접근해주세요. **/
class Global002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MARKET_002 가격이 적절하지 않습니다. **/
class Market002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MARKET_003 본인의 Market이 아닙니다. **/
class Market003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MARKET_004 본인의 본인의 NFT는 구매할 수 없습니다. **/
class Market004Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MARKET_005 본인의 유효한 기간이 아닙니다. **/
class Market005Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MEMBER_005 올바르지 않은 사용자입니다. **/
class Member005Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** NFT_005 올바르지 않은 NFT입니다. **/
class Nft005Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_005 디다 사용자의 주소입니다. **/
class Wallet005Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MARKET_006 이미 판매중인 NFT 입니다. **/
class Market006Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 407번 에러
 **/

/** GLOBAL_003 지원하지 않는 Method입니다. **/
class Global003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 409번 에러
 **/

/** POST_001 유효하지 않은 게시글입니다. **/
class Post001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** POST_002 자신의 게시글이 아닙니다. **/
class Post002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** REPORT_001 자기 자신은 신고할 수 없습니다. **/
class Report001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** REPORT_002 이미 신고한 대상입니다. **/
class Report002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** COMMENT_001 유효하지 않은 댓글입니다. **/
class Comment001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** COMMENT_002 자신의 댓글이 아닙니다. **/
class Comment002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** HIDE_001 이미 숨긴 NFT입니다. **/
class Hide001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** HIDE_002 자신의 NFT는 숨길 수 없습니다. **/
class Hide002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** HIDE_003 숨기지 않은 NFT입니다. **/
class Hide003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MARKET_001 존재하지 않는 마켓입니다. **/
class Market001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MEMBER_001 존재하지 않는 사용자입니다. **/
class Member001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MEMBER_003 중복된 사용자입니다. **/
class Member003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** MEMBER_004 중복된 닉네임입니다. **/
class Member004Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** NFT_001 유효하지 않는 NFT입니다. **/
class Nft001Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_004 3분간은 지갑을 이용할 수 없습니다. **/
class Wallet004Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_008 보유한 코인이 충분치 않습니다. **/
class Wallet008Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 415번 에러
 **/

/** GLOBAL_004 지원하지 않는 Media type입니다. **/
class Global004Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/**
 * 500번 에러
 **/

/** GLOBAL_005 서버와의 연결에 실패하였습니다. **/
class Global005Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** AUTH_006 APPLE LOGIN에 실패하였습니다. **/
class Auth006Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** NFT_002 메타데이터 생성에 실패하였습니다. **/
class Nft002Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** NFT_003 NFT 생성에 실패하였습니다. **/
class Nft003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** NFT_004 NFT 전송에 실패하였습니다. **/
class Nft004Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_003 지갑 생성에 실패하였습니다. **/
class Wallet003Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_009 KLAY 전송에 실패하였습니다. **/
class Wallet009Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_010 DIDA 확인에 실패하였습니다. **/
class Wallet010Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_011 DIDA 전송에 실패하였습니다. **/
class Wallet011Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)

/** WALLET_012 DIDA 발행에 실패하였습니다. **/
class Wallet012Exception(e: Throwable?, val url: String? = null, code: String? = null): IOException(e)
