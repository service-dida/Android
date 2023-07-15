package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetUserHideResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("userName") val userName: String,
    @SerializedName("imgUrl") val imgUrl: String,
)
