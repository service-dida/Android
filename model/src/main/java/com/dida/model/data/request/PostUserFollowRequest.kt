package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PostUserFollowRequest(
    @SerializedName("userId") val userId: String
)
