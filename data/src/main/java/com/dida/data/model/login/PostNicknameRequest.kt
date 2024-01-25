package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PostNicknameRequest(
    @SerializedName("nickname") val nickname: String,
)
