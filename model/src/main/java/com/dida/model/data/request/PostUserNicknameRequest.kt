package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostUserNicknameRequest(
    @SerializedName("nickname") val nickname: String
)
