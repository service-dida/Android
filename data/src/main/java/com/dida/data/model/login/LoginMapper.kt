package com.dida.data.model.login

import com.dida.domain.main.model.LoginToken

fun PostLoginResponse.toDomain(): LoginToken {
    return LoginToken(
        message = message,
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

fun PostNicknameResponse.toDomain(): CheckNickname {
    return CheckNickname(used = used)
}
