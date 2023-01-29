package com.dida.common.bindingadapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("isMyComments")
fun ImageView.bindIsMyComments(type: String) {
    val view = this
    when(type) {
        "MINE" -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}
