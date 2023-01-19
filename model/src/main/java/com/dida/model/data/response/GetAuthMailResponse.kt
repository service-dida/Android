package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetAuthMailResponse(
    @SerializedName("random") val random: String
)
