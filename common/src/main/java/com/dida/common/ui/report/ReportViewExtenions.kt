package com.dida.common.ui.report

import android.widget.TextView
import com.dida.common.R

fun TextView.setReportEnabled(enable: Boolean) {
    val view = this
    if(enable) {
        view.setTextColor(context.getColor(R.color.mainblack))
        view.setBackgroundResource(R.drawable.custom_brandlemon_radius10)
    } else {
        view.setTextColor(context.getColor(R.color.surface7))
        view.setBackgroundResource(R.drawable.custom_surface4_radius10)
    }
}
