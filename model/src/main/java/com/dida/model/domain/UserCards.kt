package com.dida.model.domain

data class UserCards(
    val userCardList: List<UserCard>
)

data class UserCard(
    val cardId: Long,
    val userName: String,
    val cardName: String,
    val imgUrl: String,
    val price: String,
    val liked: Boolean,
)
