package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetHotUserResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("cardCnt") val cardCnt: String,
    @SerializedName("cardUrls") val cardUrls: List<String>,
    @SerializedName("isFollowed") val isFollowed: Boolean
)
