package com.dida.domain.model.nav.swap

data class WalletAmount(
    val walletId : Long,
    val address : String,
    val dida : Double,
    val klay : Double
)