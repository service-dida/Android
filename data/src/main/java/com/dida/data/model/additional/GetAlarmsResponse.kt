package com.dida.data.model.additional

import com.dida.domain.main.model.Alarm
import com.google.gson.annotations.SerializedName

data class GetAlarmsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("response") val response: List<Alarm>,
)
