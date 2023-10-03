package com.dida.domain.main.model

data class Main(
    val getHotItems: List<HotItem>,
    val getHotSellers: List<HotSeller>,
    val getRecentNfts: List<RecentNft>,
    val getHotMembers: List<HotMember>,
)
