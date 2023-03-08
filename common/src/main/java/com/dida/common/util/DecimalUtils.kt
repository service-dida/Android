package com.dida.common.util

import java.text.DecimalFormat

fun removeTrailingDot(input: String): String {
    return if (input.endsWith('.')) {
        input.dropLast(1)
    } else {
        input
    }
}

fun addPriceDot(input : String) : String {
    val number = input.toLong()

    val formatter = DecimalFormat("#,###")
    val formatterNumber = formatter.format(number)

    return formatterNumber
}
