package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class PatchProfileDescriptionRequest(
    @SerializedName("description") val description: String,
)
