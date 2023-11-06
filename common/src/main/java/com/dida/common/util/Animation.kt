package com.dida.common.util

import android.view.View
import android.view.animation.ScaleAnimation

fun View.increaseAnimate() {
    val increaseScaleAnimation = ScaleAnimation(
        0f, 1.2f, 0f, 1.2f,
        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
        ScaleAnimation.RELATIVE_TO_SELF, 0.5f
    )
    increaseScaleAnimation.duration = 200
    this.startAnimation(increaseScaleAnimation)
}
