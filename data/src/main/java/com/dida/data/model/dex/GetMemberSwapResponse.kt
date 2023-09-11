package com.dida.data.model.dex

import com.dida.domain.main.model.Swap
import com.google.gson.annotations.SerializedName

data class GetMemberSwapResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<Swap>,
)
