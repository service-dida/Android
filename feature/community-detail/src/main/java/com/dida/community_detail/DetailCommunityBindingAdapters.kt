package com.dida.community_detail

import android.view.View
import android.widget.ImageView
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

@BindingAdapter("icSendAble")
fun ImageView.bindIcSendAble(text: String) {
    if(text.isEmpty()){
        this.setImageResource(R.drawable.ic_send_disabled)
        this.isEnabled = false
    }else{
        this.setImageResource(R.drawable.ic_send_enabled)
        this.isEnabled = true
    }
}
