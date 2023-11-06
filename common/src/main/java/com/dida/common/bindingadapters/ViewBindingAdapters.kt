package com.dida.common.bindingadapters

import android.view.View
import android.widget.CompoundButton
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.dida.common.util.OnDoubleClickListener
import com.dida.common.util.OnSingleCheckedChangeListener
import com.dida.common.util.OnSingleClickListener

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

@BindingAdapter(value = ["onFirstClick", "onDoubleClick"], requireAll = false)
fun View.setOnDoubleClickListener(
    singleClickListener: View.OnClickListener?,
    doubleClickListener: View.OnClickListener?
) {
    doubleClickListener?.also {
        setOnClickListener(
            OnDoubleClickListener(singleClickListener = singleClickListener, doubleClickListener = it)
        )
    } ?: setOnClickListener(null)
}


@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.isVisible = visible
}
