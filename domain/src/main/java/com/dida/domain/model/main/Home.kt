package com.dida.domain.model.main

data class Home(
    val getHotItems: List<Hots>,
    val getHotSellers: List<HotSeller>,
    val getRecentCards: List<UserNft>,
    val getHotUsers: List<Collection>
)