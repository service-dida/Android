package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PutCommentCommentIdRequest(
    @SerializedName("content") val content: String
)
