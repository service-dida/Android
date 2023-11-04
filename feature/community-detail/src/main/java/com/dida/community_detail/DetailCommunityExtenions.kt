package com.dida.community_detail

import android.content.Context
import android.view.View
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.dida.common.ballon.DefaultBalloon
import com.skydoves.balloon.showAlignBottom

fun View.showReportBalloon(
    context: Context,
    postId: Long,
    eventListener: DetailCommunityActionHandler
) {
    val view = this
    val balloon = DefaultBalloon.Builder()
        .firstButton(
            label = context.getString(com.dida.common.R.string.report_message_balloon),
            icon = com.dida.common.R.drawable.ic_report,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() = eventListener.onPostReport(postId = postId)
            })
        .secondButton(
            label = context.getString(com.dida.common.R.string.block_message_balloon),
            icon = com.dida.common.R.drawable.ic_block,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() = eventListener.onPostBlockClicked(postId = postId)
            })
        .build()
        .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())

    view.showAlignBottom(balloon)
}

fun View.showUpdateBalloon(
    context: Context,
    postId: Long,
    eventListener: DetailCommunityActionHandler
) {
    val view = this
    val balloon = DefaultBalloon.Builder()
        .firstButton(
            label = context.getString(com.dida.common.R.string.update_message_balloon),
            icon = com.dida.common.R.drawable.ic_profile_edit,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() {
                    eventListener.onUpdatePost(postId = postId)
                }
            })
        .secondButton(
            label = context.getString(com.dida.common.R.string.delete_message_balloon),
            icon = com.dida.common.R.drawable.ic_delete,
            listener = object : DefaultBalloon.OnClickListener {
                override fun onClick() {
                    eventListener.onDeletePostDialog(postId = postId)
                }
            })
        .build()
        .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())
    view.showAlignBottom(balloon)
}
