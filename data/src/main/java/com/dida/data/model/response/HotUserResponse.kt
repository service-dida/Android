package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class HotUserResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("count") val count: String,
    @SerializedName("followed") val followed: Boolean
)
