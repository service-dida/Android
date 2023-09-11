package com.dida.data.model.additional

import com.google.gson.annotations.SerializedName

data class PostReportRequest(
    @SerializedName("reportedId") val reportedId: Long,
    @SerializedName("description") val description: String,
)
