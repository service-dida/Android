package com.dida.domain.model.nav.home

import com.dida.domain.model.nav.mypage.UserNft

data class Home(
    val getHotItems: List<Hots>,
    val getHotSellers: List<HotSeller>,
    val getRecentCards: List<UserNft>,
    val getHotUsers: List<Collection>
)