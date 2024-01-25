package com.dida.data.model.main

import com.dida.domain.main.model.SoldOut
import com.google.gson.annotations.SerializedName

data class GetSoldOutResponse(
    @SerializedName("nftAndMemberInfos") val soldOuts: List<SoldOut>
)
