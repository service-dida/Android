package com.dida.common.util

fun removeTrailingDot(input: String): String {
    return if (input.endsWith('.')) {
        input.dropLast(1)
    } else {
        input
    }
}
