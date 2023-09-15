package com.dida.data.model.main

import com.dida.domain.main.model.HotItem
import com.dida.domain.main.model.HotMember
import com.dida.domain.main.model.HotSeller
import com.dida.domain.main.model.RecentNft
import com.google.gson.annotations.SerializedName

data class GetMainResponse(
    @SerializedName("getHotItems") val getHotItems: List<HotItem>,
    @SerializedName("getHotSellers") val getHotSellers: List<HotSeller>,
    @SerializedName("getRecentNfts") val getRecentNfts: List<RecentNft>,
    @SerializedName("getHotMembers") val getHotMembers: List<HotMember>,
)
