package com.dida.android.util

import android.content.Context

@Deprecated("Padding으로 전환")
class ConvertDpToPx {
    fun convertDPtoPX(context : Context, dp: Int): Int {
        val density: Float = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }
}
