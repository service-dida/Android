package com.dida.domain.model.nav.swap_history

data class SwapHistory(
    val sendCoinType: String,
    val receiveCoinType: String,
    val sendAmount: Double,
    val time: String
)