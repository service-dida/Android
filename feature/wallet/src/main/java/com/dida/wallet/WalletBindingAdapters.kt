package com.dida.wallet

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("historyType")
fun ImageView.bindHistoryType(type: String) {
    val view = this
    when(type) {
        "BUY" -> view.setImageResource(R.drawable.ic_wallet_buy_icon)
        else -> view.setImageResource(R.drawable.ic_wallet_sell_icon)
    }
}
