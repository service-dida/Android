package com.dida.data.model.additional

import com.google.gson.annotations.SerializedName

data class GetAppVersionResponse(
    @SerializedName("versionId") val versionId: Long,
    @SerializedName("version") val version: String,
    @SerializedName("changes") val changes: String,
    @SerializedName("essentialUpdate") val essentialUpdate: Boolean
)
