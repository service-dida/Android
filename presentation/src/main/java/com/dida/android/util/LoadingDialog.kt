package com.dida.android.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.dida.android.R

class LoadingDialog constructor(context: Context) : Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.progressBar)
        lottieAnimationView.playAnimation()
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}