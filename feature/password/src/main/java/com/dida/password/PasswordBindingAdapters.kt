package com.dida.password

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("wrongPasswordCount")
fun TextView.bindWrongPasswordCount(count: Int) {
    val view = this
    val text = "${count}/5"
    view.text = text
}
