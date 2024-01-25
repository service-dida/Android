package com.dida.data.model.additional

import com.google.gson.annotations.SerializedName

data class PostNftLikeRequest(
    @SerializedName("nftId") val nftId: Long
)
