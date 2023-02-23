package com.dida.common.bindingadapters

import android.view.View
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import com.dida.common.util.OnSingleCheckedChangeListener
import com.dida.common.util.OnSingleClickListener

@BindingAdapter("onSingleClick")
fun View.bindOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

@BindingAdapter("onSingleCheckedChange")
fun CompoundButton.bindOnSingleCheckedChangeListener(checkedChangeListener: CompoundButton.OnCheckedChangeListener?) {
    checkedChangeListener?.also {
        setOnCheckedChangeListener(OnSingleCheckedChangeListener(it))
    } ?: setOnCheckedChangeListener(null)
}
