package com.dida.data.model.main

import com.dida.domain.main.model.MemberInfo
import com.dida.domain.main.model.NftInfo
import com.google.gson.annotations.SerializedName

data class GetSoldOutResponse(
    @SerializedName("nftInfo") val nftInfo: List<NftInfo>,
    @SerializedName("memberInfo") val memberInfo: List<MemberInfo>,
)
