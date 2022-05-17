package com.dida.android.domain.model.login

import com.google.gson.annotations.SerializedName

data class SocialLoginResponse(
    @SerializedName("result") val result: User
)