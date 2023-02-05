package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PutUserDescriptionRequest(
    @SerializedName("description") val description: String
)
