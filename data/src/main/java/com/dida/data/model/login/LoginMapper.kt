package com.dida.data.model.login

import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.Password
import com.dida.domain.main.model.PublicKey

fun PostLoginResponse.toDomain(): LoginToken {
    return LoginToken(
        message = message,
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

fun GetPublicKeyResponse.toDomain(): PublicKey {
    return PublicKey(publicKey)
}

fun PostCheckPasswordResponse.toDomain(): Password {
    return Password(
        matched = matched,
        wrongCnt = wrongCnt
    )
}

