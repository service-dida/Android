package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class PostNicknameResponse(
    @SerializedName("used") val used: Boolean
)
