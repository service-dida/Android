package com.dida.common.ballon

import android.view.View
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.dida.common.R
import com.dida.common.actionhandler.CommentActionHandler
import com.skydoves.balloon.showAlignBottom

fun View.showReportBalloon(
    userId: Long,
    listener: CommentActionHandler
) {
    val view = this
    val balloon = DefaultBalloon.Builder()
        .firstButton(
            label = context.getString(R.string.report_message_balloon),
            icon = R.drawable.ic_report,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() = listener.onReportClicked(userId = userId)
            })
        .secondButton(
            label = context.getString(R.string.block_message_balloon),
            icon = R.drawable.ic_block,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() = listener.onBlockClicked(userId = userId)
            })
        .build()
        .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())

    view.showAlignBottom(balloon)
}

fun View.showUpdateBalloon(
    commentId: Long,
    listener: CommentActionHandler
) {
    val view = this
    val balloon = DefaultBalloon.Builder()
        .firstButton(
            label = context.getString(R.string.update_message_balloon),
            icon = R.drawable.ic_profile_edit,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() = listener.onUpdateClicked(commentId = commentId)
            })
        .secondButton(
            label = context.getString(R.string.delete_message_balloon),
            icon = R.drawable.ic_delete,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() = listener.onDeleteClicked(commentId = commentId)
            })
        .build()
        .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())
    view.showAlignBottom(balloon)
}
