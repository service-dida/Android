package com.dida.data.model.dex

import com.dida.domain.Contents
import com.dida.domain.main.model.Swap
import com.dida.domain.main.model.TransactionInfo

fun GetMemberSwapResponse.toDomain(): Contents<Swap> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetTransactionInfoResponse.toDomain(): Contents<TransactionInfo> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}
