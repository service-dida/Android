package com.dida.data.model.dex

import com.dida.domain.Contents
import com.dida.domain.main.model.Swap

fun GetMemberSwapResponse.toDomain(): Contents<Swap> {
    return Contents(
        page = pageSize,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}
