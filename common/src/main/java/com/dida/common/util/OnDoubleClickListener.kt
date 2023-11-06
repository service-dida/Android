package com.dida.common.util

import android.view.View

class OnDoubleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 250
) : View.OnClickListener {
    private var clickCount: Int = 0

    override fun onClick(v: View?) {
        clickCount += 1
        if (clickCount == 1) {
            v?.run {
                postDelayed({
                    clickCount = 0
                }, intervalMs)
            }
        } else if (clickCount == 2) {
            clickCount = 0
            clickListener.onClick(v)
        }
    }
}
