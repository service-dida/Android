package com.dida.data.model.market

import com.dida.domain.main.model.Nft

fun GetNftResponse.toDomain(): Nft {
    return Nft(nftInfo, description, memberInfo, tokenId, contractAddress, followed, liked)
}
