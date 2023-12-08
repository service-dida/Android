package com.dida.data.model.market

import com.dida.domain.main.model.OwnHistory
import com.google.gson.annotations.SerializedName

data class GetOwnHistoryResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<OwnHistory>,
)