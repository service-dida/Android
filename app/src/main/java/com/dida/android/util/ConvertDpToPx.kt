package com.dida.android.util

import android.content.Context

class ConvertDpToPx {
    fun convertDPtoPX(context : Context, dp: Int): Int {
        val density: Float = context.getResources().getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }
}