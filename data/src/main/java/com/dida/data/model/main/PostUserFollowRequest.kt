package com.dida.data.model.main

import com.google.gson.annotations.SerializedName

data class PostUserFollowRequest(
    @SerializedName("userId") val userId: Long
)