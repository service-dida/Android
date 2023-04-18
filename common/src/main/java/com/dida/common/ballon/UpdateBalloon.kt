package com.dida.common.ballon

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.dida.common.actionhandler.UpdateActionHandler
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.skydoves.balloon.*

class UpdateBalloon(
    private val eventListener: UpdateActionHandler
) : Balloon.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        val balloon = createBalloon(context) {
            setLayout(com.dida.common.R.layout.balloon_update)
            setIsVisibleArrow(false)
            setWidth(220)
            setMarginHorizontal(16)
            setHeight(BalloonSizeSpec.WRAP)
            setCornerRadius(12f)
            setBackgroundColor(ContextCompat.getColor(context, com.dida.common.R.color.surface5))
            setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            setLifecycleOwner(lifecycle)
        }

        balloon.getContentView().findViewById<ConstraintLayout>(com.dida.common.R.id.update_button).setOnSingleClickListener {
            eventListener.onUpdateClicked()
            balloon.dismiss()
        }

        balloon.getContentView().findViewById<ConstraintLayout>(com.dida.common.R.id.delete_button).setOnSingleClickListener {
            eventListener.onDeleteClicked()
            balloon.dismiss()
        }

        return balloon
    }
}
