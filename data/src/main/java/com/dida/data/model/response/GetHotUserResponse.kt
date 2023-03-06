package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetHotUserResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("cardCnt") val cardCnt: Long,
    @SerializedName("cardUrls") val cardUrls: List<String>,
    @SerializedName("followed") val followed: Boolean
)
