package com.dida.common.util

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.CycleInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import kotlinx.coroutines.delay

@SuppressLint("UseCompatLoadingForDrawables")
suspend fun ImageView.likeAnimation(
    like: Boolean
) {
    val imageSrc = if (like) context.getDrawable(com.dida.common.R.drawable.ic_empty_heart) else context.getDrawable(com.dida.common.R.drawable.ic_fill_heart)
    val imageView = this
    imageView.setImageDrawable(imageSrc)
    val animSet = AnimationSet(true)
    val scaleAnimation = ScaleAnimation(
        0f, 1.2f, 0f, 1.2f,
        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
        ScaleAnimation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = 200
    }

    val rotationAnimation = RotateAnimation(
        -20f, 20f, Animation.RELATIVE_TO_SELF,
        0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = 300
        startOffset = 50
        repeatMode = Animation.REVERSE
        interpolator = CycleInterpolator(10f)
    }
    animSet.addAnimation(scaleAnimation)
    animSet.addAnimation(rotationAnimation)

    imageView.visibility = View.VISIBLE
    imageView.startAnimation(animSet)
    delay(500)
    imageView.visibility = View.GONE
}
