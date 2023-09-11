package com.dida.data.interceptor

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   * 사용시 주석 풀고 사용하기
*/

//class BearerInterceptor : Interceptor {
//    //todo 조건 분기로 인터셉터 구조 변경
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        var accessToken = ""
//        val request = chain.request()
//        val response = chain.proceed(request)
//
//        when(response.code) {
//            in 400 .. 500 -> {
//                val requestUrl = request.url.toString()
//                val errorResponse = response.body?.string()?.let { createErrorResponse(it) }
//                val errorException = createErrorException(requestUrl, response.code, errorResponse)
//
//                when(errorException) {
//                    is InvalidJwtTokenException -> {
//                        runBlocking {
//                            //토큰 갱신 api 호출
//                            DataApplication.dataStorePreferences.getRefreshToken()?.let {
//                                handleApi {
//                                    Retrofit.Builder()
//                                        .baseUrl(BASE_URL)
//                                        .addConverterFactory(GsonConverterFactory.create())
//                                        .build()
//                                        .create(MainAPIService::class.java).refreshtokenAPIServer(it)
//                                }
//                            }?.onSuccess {
//                                DataApplication.dataStorePreferences.setAccessToken(it.accessToken ?: "", it.refreshToken ?: "")
//                                accessToken = it.accessToken ?: ""
//                            }?.onError {
//                                DataApplication.dataStorePreferences.removeAccountToken()
//                                accessToken = ""
//                            }
//                        }
//                        return chain.proceed(chain.request().newBuilder().addHeader("Authorization", accessToken).build())
//                    }
//                    else -> errorException.let { throw it }
//                }
//
//
//            }
//
//            else -> return response
//        }
//    }
//}
