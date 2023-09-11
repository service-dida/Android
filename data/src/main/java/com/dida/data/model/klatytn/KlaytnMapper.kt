package com.dida.data.model.klatytn

import com.dida.domain.klaytn.Asset

fun AssetResponse.toDomain(): Asset {
    return Asset(
        contentType, filename, uri
    )
}
