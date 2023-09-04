package com.dida.add.main

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("hasNext")
fun TextView.bindHasNext(hasNext: Boolean) {
    val textView = this
    textView.setTextColor(
        if (hasNext) context.getColor(com.dida.common.R.color.brand_lemon)
        else context.getColor(com.dida.common.R.color.surface5)
    )
}
