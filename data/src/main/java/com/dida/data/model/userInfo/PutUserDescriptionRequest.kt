package com.dida.data.model.userInfo

import com.google.gson.annotations.SerializedName

data class PutUserDescriptionRequest(
    @SerializedName("description") val description: String
)
