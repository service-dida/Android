package com.dida.data.model.dex

import com.dida.domain.main.model.DealingHistory
import com.dida.domain.main.model.TransactionInfo
import com.google.gson.annotations.SerializedName

data class GetTransactionsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<DealingHistory>,
)
