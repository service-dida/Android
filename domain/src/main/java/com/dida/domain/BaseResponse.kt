package com.dida.domain

enum class State {
    SUCCESS, FAIL, NETWORK_ERROR
}

data class BaseResponse(
    val state: State?,
    val data: Any?
)

