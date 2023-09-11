package com.dida.data.model.market

import com.dida.domain.main.model.MemberInfo
import com.dida.domain.main.model.NftInfo
import com.google.gson.annotations.SerializedName

data class GetNftResponse(
    @SerializedName("nftInfo") val nftInfo: NftInfo,
    @SerializedName("description") val description: String,
    @SerializedName("memberInfo") val memberInfo: MemberInfo,
    @SerializedName("tokenId") val tokenId: String,
    @SerializedName("contractAddress") val contractAddress: String,
    @SerializedName("followed") val followed: Boolean,
    @SerializedName("liked") val liked: Boolean,
)
