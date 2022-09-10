package com.dida.data.model

interface ErrorResponse {
    val timestamp: String?
    val status: Int?
    val message: String?
    val code: Int?
}

// "timestamp": "2022-08-13T21:31:59.189026",
//    "status": 400,
//    "message": "닉네임은 한자리 이상 입력하여야 합니다.",
//    "code": 200

