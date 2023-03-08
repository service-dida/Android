package com.dida.domain.model.main

data class WalletAmount(
    val walletId : Long,
    val address : String,
    val dida : Double,
    val klay : Double
)