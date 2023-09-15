package com.dida.data.model.main

import com.dida.domain.Contents
import com.dida.domain.main.model.Main
import com.dida.domain.main.model.RecentNft
import com.dida.domain.main.model.SoldOut
import com.dida.domain.main.model.Swap

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
