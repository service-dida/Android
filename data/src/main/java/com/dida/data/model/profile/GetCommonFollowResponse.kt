package com.dida.data.model.profile

import com.dida.domain.main.model.CommonFollow
import com.google.gson.annotations.SerializedName

data class GetCommonFollowResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<CommonFollow>,
)
