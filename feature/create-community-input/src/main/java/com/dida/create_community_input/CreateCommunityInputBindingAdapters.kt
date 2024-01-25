package com.dida.create_community_input

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("createBtnEnable")
fun TextView.bindCreateBtnEnable(enable: Boolean) {
    val view = this
    view.isEnabled = enable
    if(enable) view.setTextColor(ContextCompat.getColor(context, com.dida.common.R.color.white))
    else view.setTextColor(ContextCompat.getColor(context, com.dida.common.R.color.surface5))
}
