package com.dida.data.model.userInfo

import com.google.gson.annotations.SerializedName

data class PutUserNicknameRequest(
    @SerializedName("nickname") val nickname: String
)
