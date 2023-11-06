package com.dida.common.util

import android.os.Handler
import android.os.Looper
import android.view.View

class OnDoubleClickListener(
    private val singleClickListener: View.OnClickListener? = null,
    private val doubleClickListener: View.OnClickListener,
    private val intervalMs: Long = 250
) : View.OnClickListener {
    private var clickCount: Int = 0

    override fun onClick(v: View?) {
        clickCount += 1

        val handler = Handler(Looper.getMainLooper())
        val clickRunnable = Runnable {
            if (clickCount >= 2) {
                doubleClickListener.onClick(v)
            } else {
                singleClickListener?.onClick(v)
            }
            clickCount = 0
        }

        if (clickCount == 1) {
            handler.postDelayed(clickRunnable, intervalMs)
        }
    }
}
