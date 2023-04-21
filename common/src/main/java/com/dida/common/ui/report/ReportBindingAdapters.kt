package com.dida.common.ui.report

import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
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

@BindingAdapter("reportContentVisible")
fun View.bindReportContentVisible(code: ReportCode?) {
    val view = this
    code?.let {
        when (code) {
            ReportCode.OTHER -> view.isVisible = true
            else -> view.isGone = true
        }
    }
}
