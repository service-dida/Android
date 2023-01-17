package com.dida.model.domain

data class CardHideList(
    val cardHideListItems: List<CardHideListItem>
)

data class CardHideListItem(
    val cardId: Int,
    val cardUrl: Int,
    val cardTitle: Int
)
