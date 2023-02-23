package com.dida.common.util

import android.view.View
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter

@BindingAdapter("onSingleClick")
fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

@BindingAdapter("onSingleCheckedChange")
fun CompoundButton.setOnSingleCheckedChangeListener(checkedChangeListener: CompoundButton.OnCheckedChangeListener?) {
    checkedChangeListener?.also {
        setOnCheckedChangeListener(OnSingleCheckedChangeListener(it))
    } ?: setOnCheckedChangeListener(null)
}
