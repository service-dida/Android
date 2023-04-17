package com.dida.common.ballon

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.dida.common.actionhandler.ReportActionHandler
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.skydoves.balloon.*

class ReportBalloon(
    private val userId: Long,
    private val eventListener: ReportActionHandler
) : Balloon.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        val balloon = createBalloon(context) {
            setLayout(com.dida.common.R.layout.balloon_report)
            setIsVisibleArrow(false)
            setWidth(220)
            setMarginHorizontal(16)
            setHeight(BalloonSizeSpec.WRAP)
            setCornerRadius(12f)
            setBackgroundColor(ContextCompat.getColor(context, com.dida.common.R.color.surface5))
            setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            setLifecycleOwner(lifecycle)
        }

        balloon.getContentView().findViewById<ConstraintLayout>(com.dida.common.R.id.report_button).setOnSingleClickListener {
            eventListener.onReportClicked(userId)
            balloon.dismiss()
        }

        balloon.getContentView().findViewById<ConstraintLayout>(com.dida.common.R.id.block_button).setOnSingleClickListener {
            eventListener.onBlockClicked(userId)
            balloon.dismiss()
        }

        return balloon
    }
}
