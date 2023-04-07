package com.dida.domain.model.main

data class HotCard(
    val cardId: Long,
    val cardImgUrl: String,
    val title: String,
    val comments: Long
)

sealed class HotCards {
    data class Contents(
        val contents: List<HotCard>
    ) : HotCards()
}
