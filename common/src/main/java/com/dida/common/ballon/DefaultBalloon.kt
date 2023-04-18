package com.dida.common.ballon

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon

class DefaultBalloon : Balloon.Factory() {

    private var firstButtonLabel: String? = null
    private var firstButtonIcon: Int? = null
    private var firstButtonClickListener: OnClickListener? = null

    private var secondButtonLabel: String? = null
    private var secondButtonIcon: Int? = null
    private var secondButtonClickListener: OnClickListener? = null

    private lateinit var firstButtonLabelTextView: TextView
    private lateinit var firstButtonImageView: ImageView
    private lateinit var firstButton: ConstraintLayout

    private lateinit var secondButtonTextView: TextView
    private lateinit var secondButtonImageView: ImageView
    private lateinit var secondButton: ConstraintLayout

    lateinit var balloon: Balloon

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        balloon = createBalloon(context) {
            setLayout(com.dida.common.R.layout.balloon_default)
            setIsVisibleArrow(false)
            setWidth(220)
            setMarginHorizontal(16)
            setHeight(BalloonSizeSpec.WRAP)
            setCornerRadius(12f)
            setBackgroundColor(ContextCompat.getColor(context, com.dida.common.R.color.surface5))
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycle)
        }

        with(balloon.getContentView()) {
            firstButtonLabelTextView = this.findViewById(com.dida.common.R.id.first_button_text_view)
            firstButtonImageView = this.findViewById(com.dida.common.R.id.first_button_image_view)
            firstButton = this.findViewById(com.dida.common.R.id.first_button)

            secondButtonTextView = this.findViewById(com.dida.common.R.id.second_button_text_view)
            secondButtonImageView = this.findViewById(com.dida.common.R.id.second_button_image_view)
            secondButton = this.findViewById(com.dida.common.R.id.second_button)
        }

        initData()
        initView()
        return balloon
    }

    private fun initData() {
        firstButtonLabelTextView.text = firstButtonLabel
        firstButtonIcon?.let { firstButtonImageView.setImageResource(it) }

        secondButtonTextView.text = secondButtonLabel
        secondButtonIcon?.let { secondButtonImageView.setImageResource(it) }
    }

    private fun initView() {
        firstButton.setOnSingleClickListener {
            firstButtonClickListener?.onClick()
            balloon.dismiss()
        }

        secondButton.setOnSingleClickListener {
            secondButtonClickListener?.onClick()
            balloon.dismiss()
        }
    }

    data class Builder(
        private var firstButtonLabel: String? = null,
        private var firstButtonIcon: Int? = null,
        private var firstButtonClickListener: OnClickListener? = null,
        private var secondButtonLabel: String? = null,
        private var secondButtonIcon: Int? = null,
        private var secondButtonClickListener: OnClickListener? = null
    ) {
        fun firstButton(label: String, icon: Int, listener: OnClickListener) = apply {
            this.firstButtonLabel = label
            this.firstButtonIcon = icon
            this.firstButtonClickListener = listener
        }

        fun secondButton(label: String, icon: Int, listener: OnClickListener) = apply {
            this.secondButtonLabel = label
            this.secondButtonIcon = icon
            this.secondButtonClickListener = listener
        }

        fun build() = DefaultBalloon().also {
            it.firstButtonLabel = firstButtonLabel
            it.firstButtonIcon = firstButtonIcon
            it.firstButtonClickListener = firstButtonClickListener
            it.secondButtonLabel = secondButtonLabel
            it.secondButtonIcon = secondButtonIcon
            it.secondButtonClickListener = secondButtonClickListener
        }
    }

    interface OnClickListener {
        fun onClick()
    }
}
