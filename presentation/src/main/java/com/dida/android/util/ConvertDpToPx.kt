package com.dida.android.util

import android.content.Context

class ConvertDpToPx {
    fun convertDPtoPX(context : Context, dp: Int): Int {
        val density: Float = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }
}