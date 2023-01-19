package com.dida.model.domain

data class CardResponse(
    val cardId: Int,
    val title: String,
    val description: String,
    val imgUrl: String,
    val userId: Int,
    val nickname: String,
    val profileUrl: String,
    val id: String,
    val contracts: String?,
    val liked: Boolean,
    val type: String,
    val price: String
)
