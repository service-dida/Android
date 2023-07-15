package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class DeleteUserHideRequest(
    @SerializedName("userId") val userId: Long
)