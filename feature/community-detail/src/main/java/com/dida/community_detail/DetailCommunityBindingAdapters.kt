package com.dida.community_detail

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("moreVisible")
fun ConstraintLayout.bindMoreVisible(type: String?) {
    val view = this
    when(type) {
        "MINE" -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}
