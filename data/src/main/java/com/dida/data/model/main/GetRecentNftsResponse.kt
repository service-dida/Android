package com.dida.data.model.main

import com.dida.domain.main.model.RecentNft
import com.google.gson.annotations.SerializedName

data class GetRecentNftsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<RecentNft>,
)
