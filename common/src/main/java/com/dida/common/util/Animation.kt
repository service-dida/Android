package com.dida.common.util

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.ImageView

private const val ScaleFrom: Float = 0f
private const val ScaleTo: Float = 1.2f
private const val ScaleDuration: Long = 200

private const val RotateFrom: Float = -20f
private const val RotateTo: Float = 20f
private const val RotateDuration: Long = 300

private const val PivotValue: Float = 0.5f

@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.likeAnimation(
    like: Boolean
) {
    val imageSrc = if (like) context.getDrawable(com.dida.common.R.drawable.ic_empty_heart) else context.getDrawable(com.dida.common.R.drawable.ic_fill_heart)
    val imageView = this
    imageView.setImageDrawable(imageSrc)

    val animationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            imageView.post { imageView.visibility = View.VISIBLE }
        }
        override fun onAnimationEnd(animation: Animation?) {
            imageView.postDelayed({ imageView.visibility = View.GONE }, 100)
        }
        override fun onAnimationRepeat(animation: Animation?) = Unit
    }

    val animSet = AnimationSet(true)

    val scaleAnimation = ScaleAnimation(
        ScaleFrom, ScaleTo, ScaleFrom, ScaleTo,
        ScaleAnimation.RELATIVE_TO_SELF, PivotValue,
        ScaleAnimation.RELATIVE_TO_SELF, PivotValue
    ).apply {
        duration = ScaleDuration
    }

    val rotationAnimation = RotateAnimation(
        RotateFrom, RotateTo,
        Animation.RELATIVE_TO_SELF, PivotValue,
        Animation.RELATIVE_TO_SELF, PivotValue
    ).apply {
        duration = RotateDuration
        repeatMode = Animation.REVERSE
    }

    animSet.apply {
        addAnimation(scaleAnimation)
        addAnimation(rotationAnimation)
        setAnimationListener(animationListener)
    }

    imageView.startAnimation(animSet)

}
