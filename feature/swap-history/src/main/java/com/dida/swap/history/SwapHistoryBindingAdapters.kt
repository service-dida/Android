package com.dida.swap.history

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.domain.main.model.Swap
import com.dida.domain.main.model.SwapType

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("SwapTypeImage")
fun ImageView.bindSwapTypeImage(swap: Swap) {
    val imageView = this
    val imgRes: Int = when (swap.getSwapType()) {
        SwapType.DIDA_TO_KLAY -> R.drawable.ic_klay
        SwapType.KLAY_TO_DIDA -> R.drawable.ic_dida
    }
    imageView.setImageDrawable(context.getDrawable(imgRes))
}

@BindingAdapter("SwapTypeTextLabel")
fun TextView.bindSwapTypeTextLabel(swap: Swap) {
    val textView = this
    val strRes = when (swap.getSwapType()) {
        SwapType.DIDA_TO_KLAY -> R.string.swap_history_didaToKlay_text
        SwapType.KLAY_TO_DIDA -> R.string.swap_history_klayToDida_text
    }
    textView.text = context.getString(strRes)
}
