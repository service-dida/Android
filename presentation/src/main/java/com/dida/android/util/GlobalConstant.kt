package com.dida.android.util

@Deprecated("BaseUrl 레이어 이전")
class GlobalConstant {
    // 코틀린의 전역변수 문법
    companion object {
        // TEST
        const val BASE_URL = "https://service-dida.shop:8000/"
        // MAIN
        // const val BASE_URL = "https://service-dida.shop:8080/"
    }
}