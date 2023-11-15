package com.dida.common.util

import android.view.HapticFeedbackConstants
import android.view.View

fun View.performHapticEvent() {
    val view = this
    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
}
