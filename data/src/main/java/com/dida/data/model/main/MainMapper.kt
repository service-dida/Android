package com.dida.data.model.main

import com.dida.domain.Contents
import com.dida.domain.main.model.RecentNft
import com.dida.domain.main.model.Swap

fun GetRecentNftsResponse.toDomain(): Contents<RecentNft> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}
