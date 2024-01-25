package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class PostCheckPasswordResponse(
    @SerializedName("matched") val matched: Boolean,
    @SerializedName("wrongCnt") val wrongCnt: Int
)
