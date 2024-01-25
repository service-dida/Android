package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class PostUserNicknameResponse(
    @SerializedName("used") val used: Boolean
)
