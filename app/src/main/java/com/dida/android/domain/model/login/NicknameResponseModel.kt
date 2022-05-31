package com.dida.android.domain.model.login

import com.google.gson.annotations.SerializedName

data class NicknameResponseModel(
    // 해당 닉네임이 사용중이라면 True, 아니라면 False를 반환합니다.
    @SerializedName("used")
    val used: Boolean
)