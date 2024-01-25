package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetAppVersionResponse(
    @SerializedName("version") val version: Int
)
