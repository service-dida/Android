package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PatchPostPostIdRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)
