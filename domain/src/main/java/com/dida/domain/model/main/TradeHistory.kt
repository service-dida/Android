package com.dida.domain.model.main

data class TradeHistory(
    val cardId: Long,
    val cardName: String,
    val cardImgUrl: String,
    val userName: String,
    val price: Float,
    val type: String
)
