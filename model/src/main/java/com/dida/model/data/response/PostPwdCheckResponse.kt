package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class PostPwdCheckResponse(
    @SerializedName("flag") val flag: Boolean
)
