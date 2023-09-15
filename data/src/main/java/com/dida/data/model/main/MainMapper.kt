package com.dida.data.model.main

import com.dida.domain.Contents
import com.dida.domain.main.model.HotSellerPage
import com.dida.domain.main.model.Main
import com.dida.domain.main.model.RecentNft
import com.dida.domain.main.model.SoldOut

fun GetRecentNftsResponse.toDomain(): Contents<RecentNft> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetMainResponse.toDomain(): Main {
    return Main(getHotItems, getHotSellers, getRecentNfts, getHotMembers)
}

fun GetSoldOutResponse.toDomain(): SoldOut {
    return SoldOut(nftInfo, memberInfo)
}

fun GetSoldOutPageResponse.toDomain(): Contents<SoldOut> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetHotSellerPageResponse.toDomain(): Contents<HotSellerPage> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

