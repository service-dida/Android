package com.dida.domain.main.model


data class Swap(
    val transactionId: Long,
    val type: String,
    val coin: Float,
    val time: String,
) {
    fun getAmountUnit(): String {
        return if(type == "K") coin.toString()+"klay" else coin.toString()+"dida"
    }
}
