package com.dida.data.model.market

import com.dida.data.model.additional.GetAlarmsResponse
import com.dida.domain.Contents
import com.dida.domain.main.model.Alarm
import com.dida.domain.main.model.Nft
import com.dida.domain.main.model.OwnHistory

fun GetNftResponse.toDomain(): Nft {
    return Nft(nftInfo, description, memberInfo, tokenId, contractAddress, followed, liked, marketId, me)
}

fun GetOwnHistoryResponse.toDomain(): Contents<OwnHistory> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}