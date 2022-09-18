package com.dida.data.model.klaytn

import com.google.gson.annotations.SerializedName

data class NFTMintRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String
)
