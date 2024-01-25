package com.dida.model.domain

data class CardsPostLikes(
    val cardPostLikes: List<CardsPostLike>
)

data class CardsPostLike(
    val cardId: Long,
    val cardImgUrl: String,
    val cardName: String,
    val userImgUrl: String,
    val userName: String
)
