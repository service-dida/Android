package com.dida.domain.main.model

data class TransactionInfo(
    val dealingHistory: DealingHistory,
    val purchased: Boolean
)

data class DealingHistory(
    val transactionId: Long,
    val nftId: Long,
    val nftTitle: String,
    val nftUrl: String,
    val price: Float
)
