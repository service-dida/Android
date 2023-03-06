package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PostUserFollowRequest(
    @SerializedName("userId") val userId: Int
)
