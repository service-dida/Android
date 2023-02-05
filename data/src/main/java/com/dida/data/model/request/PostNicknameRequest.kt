package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostNicknameRequest(
    @SerializedName("nickname") val nickname: String
)
