package com.dida.domain.main.model

data class Swap(
    val transactionId: Long,
    val type: String,
    val coin: Float,
    val time: String,
) {
    fun getAmountUnit(): String {
        return if(type == "SWAP1") coin.toString()+"dida" else coin.toString()+"klay"
    }

    fun getSwapType(): SwapType {
        val response = this.type
        return if (response == "SWAP1") SwapType.KLAY_TO_DIDA else SwapType.DIDA_TO_KLAY
    }
}

enum class SwapType {
    KLAY_TO_DIDA, DIDA_TO_KLAY
}
