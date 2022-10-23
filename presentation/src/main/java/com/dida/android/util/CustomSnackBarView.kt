package com.dida.android.util

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dida.android.R
import com.dida.android.databinding.ViewCustomSnackBarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBarView(view: View, private val message: String) {
    companion object {
        fun make(view: View, message: String) = CustomSnackBarView(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: ViewCustomSnackBarBinding = DataBindingUtil.inflate(inflater, R.layout.view_custom_snack_bar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.message.text = message
        snackbarBinding.actionBtn.setOnClickListener {
            // do something..
        }
    }

    fun show() {
        snackbar.show()
    }
}