package com.dida.data.model.additional

import com.google.gson.annotations.SerializedName

data class GetKeywordsResponse(
    @SerializedName("things") val things: List<String>,
    @SerializedName("places") val places: List<String>
)
