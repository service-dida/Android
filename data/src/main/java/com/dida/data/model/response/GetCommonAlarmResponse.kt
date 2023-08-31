package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetCommonAlarmResponse(
    @SerializedName("alarmId") val alarmId: Long,
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: Long,
    @SerializedName("watched") val watched: Boolean,
    @SerializedName("date") val date: String
)
