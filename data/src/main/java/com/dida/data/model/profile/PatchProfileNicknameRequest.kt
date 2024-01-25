package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class PatchProfileNicknameRequest(
    @SerializedName("nickname") val nickname: String,
)
