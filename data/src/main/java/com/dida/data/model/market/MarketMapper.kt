package com.dida.data.model.market

import com.dida.domain.Contents
import com.dida.domain.main.model.Nft
import com.dida.domain.main.model.OwnershipHistory

fun GetNftResponse.toDomain(): Nft {
    return Nft(nftInfo, description, memberInfo, tokenId, contractAddress, followed, liked, marketId, me)
}

fun GetOwnershipHistoryResponse.toDomain(): Contents<OwnershipHistory> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}