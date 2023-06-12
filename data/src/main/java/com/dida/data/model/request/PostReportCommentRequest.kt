package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostReportCommentRequest(
    @SerializedName("reportedId") val reportedId: Long,
    @SerializedName("content") val content: String
)
