package com.dida.data.mapper

import com.dida.data.model.response.PostNicknameResponse
import com.dida.data.model.response.login.PostLoginResponse
import com.dida.domain.model.main.Nickname
import com.dida.domain.model.main.Token

fun PostLoginResponse.toDomain(): Token {
    return Token(
        message = this.message,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun PostNicknameResponse.toDomain(): Nickname {
    return Nickname(
        used = this.used
    )
}
