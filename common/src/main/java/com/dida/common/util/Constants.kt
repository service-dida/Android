package com.dida.common.util

object Constants {
    const val MAX_AMOUNT = 20000000
    const val MAX_AMOUNT_TEXT = "20,000,000"

    const val MIN_AMOUNT = 0.01
    const val MIN_AMOUNT_TEXT = "0.01"

    const val ROLLING_INTERVAL_MS = 750L
    const val REPEAT_INTERVAL_MS = 2850L
}

enum class SLIDETYPE {
    CAROUSEL, INFINITE, NORMAL
}
