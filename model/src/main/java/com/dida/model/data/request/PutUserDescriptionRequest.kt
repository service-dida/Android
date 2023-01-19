package com.dida.model.data.request

import com.google.gson.annotations.SerializedName

data class PutUserDescriptionRequest(
    @SerializedName("description") val description: String
)
