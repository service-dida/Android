package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PostNicknameResponse(
    @SerializedName("used") val used: Boolean
)
