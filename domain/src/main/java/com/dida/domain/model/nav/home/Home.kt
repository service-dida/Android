package com.dida.domain.model.nav.home

import com.dida.domain.model.nav.mypage.UserNft
import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("getHotItems") val getHotItems: List<Hots>,
    @SerializedName("getHotSellers") val getHotSellers: List<HotSeller>,
    @SerializedName("getRecentCards") val getRecentCards: List<UserNft>,
    @SerializedName("getHotUsers") val getHotUsers: List<Collection>
)