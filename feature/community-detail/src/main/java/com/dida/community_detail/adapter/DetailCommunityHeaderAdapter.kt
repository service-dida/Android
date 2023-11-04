package com.dida.community_detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.ballon.DefaultBalloon
import com.dida.community_detail.DetailCommunityActionHandler
import com.dida.community_detail.R
import com.dida.community_detail.databinding.HolderDetailCommunityHeaderBinding
import com.dida.data.DataApplication
import com.dida.domain.main.model.Post
import com.skydoves.balloon.showAlignBottom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailCommunityHeaderAdapter(
    private val eventListener: DetailCommunityActionHandler
) : ListAdapter<Post, DetailCommunityHeaderAdapter.ViewHolder>(DetailCommunityHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderDetailCommunityHeaderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_detail_community_header,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding, eventListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(item = it) }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(
        private val binding: HolderDetailCommunityHeaderBinding,
        val eventListener: DetailCommunityActionHandler
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.holder = item
            binding.moreButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val userId = DataApplication.dataStorePreferences.getUserId()
                    if (userId == item.memberInfo.memberId) {
                        showUpdateBalloon(context = binding.root.context, postId = item.postInfo.postId, view = it)
                    } else {
                        showReportBalloon(context = binding.root.context, postId = item.postInfo.postId, view = it)
                    }
                }

            }
            binding.executePendingBindings()
        }

        private fun showReportBalloon(
            context: Context,
            postId: Long,
            view: View
        ) {
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

        private fun showUpdateBalloon(
            context: Context,
            postId: Long,
            view: View
        ) {
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
    }

    internal object DetailCommunityHeaderDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
