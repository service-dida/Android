package com.dida.common.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.dida.common.R
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.databinding.DefaultSnackBarBinding
import com.google.android.material.snackbar.Snackbar

class DefaultSnackBar {

    private var view: View? = null
    private var message: String? = null
    private var actionButtonLabel: String? = null
    private var hasBottomMargin: Boolean? = true
    private var actionButtonListener: OnClickListener? = null

    private var context: Context? = null
    private var snackBar: Snackbar? = null
    private var snackBarLayout: Snackbar.SnackbarLayout? = null

    private var inflater: LayoutInflater? = null
    private var binding: DefaultSnackBarBinding? = null

    private val isMessageOnly: Boolean
        get() = (actionButtonListener == null) && actionButtonLabel.isNullOrBlank()

    init {
        initialize()
        initView()
        initData()
        show()
    }

    private fun initialize() {
        context = view?.context
        snackBar = Snackbar.make(view!! , "", 1500)
        snackBarLayout = snackBar?.view as Snackbar.SnackbarLayout
        inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater!!, R.layout.default_snack_bar, null, false)
    }

    private fun initView() {
        snackBarLayout?.let { layout ->
            val layoutParams = layout.layoutParams as FrameLayout.LayoutParams

            val snackBarShowAnim = AnimationUtils.loadAnimation(context, R.anim.show_snack_bar)
            val snackBarHideAnim = AnimationUtils.loadAnimation(context, R.anim.hide_snack_bar)
            layout.startAnimation(snackBarShowAnim)

            Handler(Looper.getMainLooper()).postDelayed({
                layout.startAnimation(snackBarHideAnim)
            }, 2000L)

            layoutParams.gravity = Gravity.BOTTOM
            layout.removeAllViews()
            hasBottomMargin?.let {
                if (it) layout.setPadding(20, 0, 20, 215)
                else layout.setPadding(20, 0, 20, 10)
            }

            layout.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
            layout.addView(binding?.root, 0)
        }
    }

    private fun initData() {
        binding?.let {
            it.centeralMessageTextView.isVisible = isMessageOnly
            it.messageTextView.isVisible = !isMessageOnly
            it.actionButton.isVisible = !isMessageOnly

            it.messageTextView.text = message
            it.centeralMessageTextView.text = message
            it.actionButtonTextView.text = actionButtonLabel
            it.actionButton.setOnSingleClickListener {
                actionButtonListener?.onClick()
            }
        }
    }

    private fun show() = snackBar?.show()

    data class Builder(
        private var view: View? = null,
        private var message: String? = null,
        private var hasBottomMargin: Boolean? = true,
        private var actionButtonLabel: String? = null,
        private var actionButtonListener: OnClickListener? = null,
    ) {

        fun view(view: View) = apply { this.view = view }

        fun message(message: String) = apply { this.message = message }

        fun actionButton(label: String, listener: OnClickListener? = null) = apply {
            this.actionButtonLabel = label
            this.actionButtonListener = listener
        }

        fun hasBottomMargin(hasBottomMargin: Boolean) = apply { this.hasBottomMargin = hasBottomMargin }

        fun build() = DefaultSnackBar().also {
            it.view = view
            it.message = message
            it.hasBottomMargin = hasBottomMargin
            it.actionButtonLabel = actionButtonLabel
            it.actionButtonListener = actionButtonListener
        }
    }

    interface OnClickListener {
        fun onClick()
    }
}
