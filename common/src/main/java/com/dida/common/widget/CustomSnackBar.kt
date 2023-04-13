package com.dida.common.widget

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dida.common.R
import com.dida.common.databinding.CustomSnackBarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val message: String) {

    companion object {
        fun make(view: View, message: String) = CustomSnackBar(view, message)
    }

    private val context = view.context
    private val snackBar = Snackbar.make(view, "", 2000)
    private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackBarBinding: CustomSnackBarBinding =
        DataBindingUtil.inflate(inflater, R.layout.custom_snack_bar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackBarLayout) {
            val layoutParams = layoutParams as FrameLayout.LayoutParams

            val snackBarShowAnim = AnimationUtils.loadAnimation(context, R.anim.show_snack_bar)
            val snackBarHideAnim = AnimationUtils.loadAnimation(context, R.anim.hide_snack_bar)
            this.startAnimation(snackBarShowAnim)

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    this.startAnimation(snackBarHideAnim)
                }, 2000L
            )

            layoutParams.gravity = Gravity.BOTTOM
            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackBarBinding.root, 0)
        }

    }

    private fun initData() {
        snackBarBinding.message.text = message
    }

    fun show() {
        snackBar.show()
    }
}
