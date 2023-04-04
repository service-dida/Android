package com.dida.domain.model.main

data class HotCard(
    val cardId: Long,
    val cardImgUrl: String
)

sealed class HotCards {
    data class Contents(
        val contents: List<HotCard>
    ) : HotCards()
}
