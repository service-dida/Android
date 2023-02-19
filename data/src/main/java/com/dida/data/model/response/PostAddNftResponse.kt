package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class PostAddNftResponse(
    @SerializedName("cardId") val cardId: Long,
)
