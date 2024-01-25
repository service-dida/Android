package com.dida.data.model.sns

import com.dida.domain.main.model.OwnNft
import com.google.gson.annotations.SerializedName

data class GetOwnNftsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<OwnNft>,
)
